package com.spaceage.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceage.model.Bom;
import com.spaceage.model.Case;
import com.spaceage.model.Country;
import com.spaceage.model.ImageModel;
import com.spaceage.model.Item;
import com.spaceage.model.Item_masterDTO;
import com.spaceage.model.PackagingType;
import com.spaceage.model.PartRequestDTO;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.model.SummaryDTO;
import com.spaceage.report.CSVHelper;
import com.spaceage.report.ExcelHelper;
import com.spaceage.service.ItemMasterService;

@Repository
public class ItemMasterServiceImpl implements ItemMasterService {

	@Autowired
	Environment env;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(MultipartFile file, String lotRefNo) {
		try {
			List<Bom> fileData = CSVHelper.csvToBom(file.getInputStream(), lotRefNo);

			jdbcTemplate.batchUpdate(env.getProperty("INSERT_BOM"), new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pStmt, int i) throws SQLException {
					Bom bom = fileData.get(i);
					pStmt.setString(1, bom.getLot_ref_no());
					pStmt.setString(2, bom.getPartNo());
					pStmt.setString(3, bom.getPartDescription());
					pStmt.setString(4, bom.getVersion());
					pStmt.setString(5, bom.getStLoction());
					pStmt.setString(6, bom.getValidity());
					pStmt.setString(7, bom.getCatDescription());
					pStmt.setString(8, bom.getQtyRequired());

					pStmt.setString(9, bom.getQtyLot());
					pStmt.setString(10, bom.getPrimaryNo());
					pStmt.setString(11, bom.getSecondaryNo());
					pStmt.setString(12, bom.getPackCode());

					pStmt.setString(13, bom.getPackQty());
					pStmt.setString(14, bom.getPackingGroup());
					pStmt.setString(15, bom.getTotalNoOfPackingGroup());
					pStmt.setString(16, bom.getMixGroup());

					pStmt.setString(17, bom.getMix());
					pStmt.setString(18, bom.getBomNo());
					pStmt.setString(19, bom.getCaseMap());
					pStmt.setString(20, bom.getImages());
					pStmt.setInt(21, 1);
				}

				@Override
				public int getBatchSize() {
					return fileData.size();
				}
			});

		} catch (UncategorizedSQLException e) {

		} catch (IOException e) {
			deleteItemMaster(lotRefNo);
			e.printStackTrace();
			throw new RuntimeException("fail to store csv data: " + e.getMessage());

		} catch (Exception e) {
			deleteItemMaster(lotRefNo);
			e.printStackTrace();
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public ResponseDTO getAllItems(RequestDTO request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getSortByColumn().isEmpty()) {
			request.setSortByColumn("item_id");
			request.setSortByMode("desc");
		}
		try {
			String defaultQuery = getDefaultQery(request);
			List<Item_masterDTO> dto = null;
			try {
				dto = jdbcTemplate.query(env.getProperty("SELECT_ALL_ITEM") + defaultQuery,
						new BeanPropertyRowMapper<Item_masterDTO>(Item_masterDTO.class));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setData(dto);
			response.setTotalElements(dto.get(0).getTotalElements());
			if (!response.getData().isEmpty()) {
				response.setNoData(false);
			}
			return response;
		} catch (Exception e) {
			e.getMessage();
			response.setMessage(e.getMessage());
		}
		return null;
	}

	private String getDefaultQery(RequestDTO request) {
		return (String) " order by " + request.getSortByColumn() + " " + request.getSortByMode() + " LIMIT "
				+ request.getLimit() + " OFFSET " + request.getOffset();
	}

	@Override
	public ByteArrayInputStream load(String id) {

		ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(getBomById(id, null), findById(id));
		return in;
	}

	public Item getJson(String item) {
		Item it = new Item();
		ObjectMapper objMap = new ObjectMapper();
		try {
			it = objMap.readValue(item, Item.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return it;
	}

	public long save(Item itemJson) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(env.getProperty("INSERT_ITEM"),
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, itemJson.getCustomer_code());
				ps.setLong(2, itemJson.getProject_code());
				ps.setLong(3, itemJson.getOrg_country_id());
				ps.setLong(4, itemJson.getPacking_type());
				ps.setString(5, itemJson.getLot_size());
				ps.setLong(6, itemJson.getCustomer_login());
				ps.setString(7, itemJson.getLot_ref_no());
				ps.setString(8, itemJson.getContainers().toString());
				ps.setInt(9, 1);
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();

		/* FOR POSTGRE */
		// return (Integer) holder.getKeyList().get(0).get("item_id");
	}

	@Override
	public List<PackagingType> getPackagingType() {
		try {
			List<PackagingType> type = jdbcTemplate.query(env.getProperty("select_packagingType"),
					new BeanPropertyRowMapper<PackagingType>(PackagingType.class));

			return type;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Country> getCountry() {
		try {
			List<Country> country = jdbcTemplate.query(env.getProperty("select_country"),
					new BeanPropertyRowMapper<Country>(Country.class));

			return country;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Bom> getBomById(String id, String group) {
		List<Bom> plist = new ArrayList<>();
		String query = env.getProperty("SELECT_BOM");
		String getProjectCode = env.getProperty("SELECT_PROJECT_CODE");

		Object[] inputs = new Object[] { id };
		try {
			String projectCode = jdbcTemplate.queryForObject(getProjectCode, inputs, String.class);

			List<Map<String, Object>> bomList;
			if (group != null) {
				query = query.concat(" and packing_group =? ORDER BY CAST(bomNo as UNSIGNED) ASC ");
				bomList = jdbcTemplate.queryForList(query, id, group);
			} else {
				query = query.concat(" ORDER BY CAST(bomNo as UNSIGNED) ASC ");
				bomList = jdbcTemplate.queryForList(query, id);
			}

			bomList.forEach(m -> {
				Bom b = new Bom();
				b.setBomId((int) m.get("BOM_ID"));
				b.setLot_ref_no((String) m.get("lot_ref_no"));
				b.setPartNo((String) m.get("PART_NO"));
				b.setPartDescription((String) m.get("PART_DESCRIPTION"));
				b.setVersion((String) m.get("Version"));
				b.setStLoction((String) m.get("ST_LOCATION"));
				b.setValidity((String) m.get("VALIDITY"));
				b.setCatDescription((String) m.get("CAT_Description"));
				b.setQtyRequired((String) m.get("QTY_REQUIRED"));
				b.setQtyLot((String) m.get("Qty_Lot"));
				b.setPrimaryNo((String) m.get("Primary_NO"));
				b.setSecondaryNo((String) m.get("Secondary_NO"));
				b.setPackCode((String) m.get("Pack_Code"));
				b.setPackQty((String) m.get("Pack_Qty"));
				b.setPackingGroup((String) m.get("Packing_Group"));
				b.setTotalNoOfPackingGroup((String) m.get("Total_No_of_Packing_Group"));
				b.setMixGroup((String) m.get("Mix_Group"));
				b.setMix((String) m.get("Mix"));
				b.setBomNo((String) m.get("BomNo"));
				b.setCaseMap((String) m.get("Case_map"));
				b.setImages((String) m.get("Images"));
				b.setCreatedBy((int) m.get("CreatedBy"));
				b.setCreatedDate((Date) m.get("CreatedDate"));
				b.setModifiedBy((int) m.get("ModifiedBy"));
				b.setModifiedDate((Date) m.get("ModifiedDate"));
				b.setStatus((int) m.get("Status"));
				b.setPendingDate((Date) m.get("pendingDate"));
				b.setAckDate((Date) m.get("ackDate"));
				b.setReceivedDate((Date) m.get("receivedDate"));
				b.setPackedDate((Date) m.get("packedDate"));

				b.setProjectCode(projectCode);
				b.setEnablePartLabel(false);
				b.setEnableCaseReport(false);
				if ((int) m.get("pick_label_scan") == 0) {
					b.setLabelStatus("Pending");
					b.setColorCode("#FFFF00");
				} else if ((int) m.get("part_label_scan") > 0
						&& (int) m.get("part_label_scan") < Integer.parseInt(b.getTotalNoOfPackingGroup())) {
					b.setLabelStatus("Work In Progress");
					b.setColorCode("#00FF00");
					b.setEnablePartLabel(true);
				} else if ((int) m.get("pick_label_scan") == 2) {

					if ((int) m.get("part_label_scan") == Integer.parseInt(b.getTotalNoOfPackingGroup())) {
						b.setLabelStatus("Packed");
						b.setColorCode("#0000FF");
						b.setEnablePartLabel(true);
						b.setEnableCaseReport(true);
					} else {
						b.setLabelStatus("Received");
						b.setColorCode("#964B00");
						b.setEnablePartLabel(true);
					}

				} else if ((int) m.get("pick_label_scan") == 1) {
					b.setLabelStatus("Acknowledge");
					b.setColorCode("#FFA500");
				}

				b.setTotalPartScanned((int) m.get("part_label_scan") + "/" + b.getTotalNoOfPackingGroup());

				try {
					b.setPicByte(getImageByPartNo(b.getPartNo()));

				} catch (IOException e) {

					e.printStackTrace();
				}

				plist.add(b);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return plist;
	}

	private byte[] getImageByPartNo(String partNo) throws IOException {

		String getProjectCode = env.getProperty("SELECT_IMAGE");

		Object[] inputs = new Object[] { partNo };

		byte[] data = null;
		try {
			data = jdbcTemplate.queryForObject(getProjectCode, inputs, byte[].class);
		} catch (DataAccessException e) {
			// do nothing
		}
		if (data != null) {
			return Base64.decodeBase64(data);
		}
		return null;

	}

	@Override
	public int createBom(Bom bom) {
		try {
			if(bom.getBomId()!= 0 && !bom.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("UPDATE_BOM"), getObject(bom, true));
			}else if(bom.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("DELETE_BOM"),  new Object[] {bom.getBomId()});
			}else {
				return jdbcTemplate.update(env.getProperty("INSERT_BOM"),  getObject(bom, false));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String gunScanner(String barcode, String value) {
		Map<String, Object> out = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("scan_pick_label");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("part_no_in", barcode);
			param.put("Scan_Type_in", value);

			SqlParameterSource in = new MapSqlParameterSource().addValues(param);
			out = jdbcCall.execute(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(out.get("#update-count-1"));

	}

	@Override
	public List<String> getLotRefNo() {
		try {
			List<String> type = jdbcTemplate.query(env.getProperty("select_lotRefNo"), new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
			});

			return type;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SummaryDTO findById(String id) {

		SummaryDTO summary = null;
		try {
			summary = (SummaryDTO) jdbcTemplate.queryForObject(env.getProperty("SELECT_ITEMBYID"), new Object[] { id },
					new BeanPropertyRowMapper<SummaryDTO>(SummaryDTO.class));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return summary;
	}

	@Override
	public void deleteItemMaster(String lot_ref_no) {
		String SQL = "delete from item_master where lot_ref_no = ?";
		jdbcTemplate.update(SQL, lot_ref_no);
		return;
	}

	public Bom getBomJson(String bom) {
		Bom it = new Bom();
		ObjectMapper objMap = new ObjectMapper();
		try {
			it = objMap.readValue(bom, Bom.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return it;
	}

	@Override
	public void createCase(Bom c) {

		int ca = 0;
		try {
			ca = (int) jdbcTemplate.queryForObject(env.getProperty("SELECT_CASE_ID"),
					new Object[] { c.getLot_ref_no(), c.getBomNo(), c.getPackingGroup() }, Integer.class);

		} catch (Exception e) {

		}

		try {
			if (ca == 0) {

				jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(env.getProperty("INSERT_CASE"));
						ps.setString(1, c.getLot_ref_no());
						ps.setString(2, c.getBomNo());
						ps.setString(3, c.getPackingGroup());
						ps.setString(4, c.getNetWeight());
						ps.setString(5, c.getGrossWeight());
						return ps;
					}
				});
			} else {
				jdbcTemplate.update(
						"UPDATE case_weight SET netweight = ?, grossweight = ?  WHERE lot_ref_no=? and bomno=? and packinggroup=? ",
						new Object[] { c.getNetWeight(), c.getGrossWeight(), c.getLot_ref_no(), c.getBomNo(),
								c.getPackingGroup() });

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Case getCase(Bom c) {

		return (Case) jdbcTemplate.queryForObject(env.getProperty("SELECT_CASE_ID"),
				new Object[] { c.getLot_ref_no(), c.getBomNo(), c.getPackingGroup() },
				new BeanPropertyRowMapper(Case.class));
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

	@Override
	public List<Bom> getBomByIdForLable(String lotRefNo, String bomId) {

		String sql = null;
		String id = null;
		List<Bom> response = new ArrayList<>();
		// picklabel
		if (lotRefNo != null) {
			sql = env.getProperty("SELECT_BOM_IMAGE");
			id = lotRefNo;
		}
		// partlabel
		if (bomId != null) {
			sql = env.getProperty("SELECT_BOM_BYID");
			id = bomId;
		}

		List<Bom> item = null;
		try {
			item = jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper(Bom.class));
			
			if(bomId != null) {
				if(Integer.parseInt(item.get(0).getTotalNoOfPackingGroup()) > 0) {
					for(int i =0; i<Integer.parseInt(item.get(0).getTotalNoOfPackingGroup()); i++) {
						Bom b = new Bom();
						b.setBomId((int) item.get(0).getBomId());
						b.setLot_ref_no((String) item.get(0).getLot_ref_no());
						b.setPartNo((String) item.get(0).getPartNo());
						b.setPartDescription((String) item.get(0).getPartDescription());
						b.setVersion((String) item.get(0).getVersion());
						b.setStLoction((String) item.get(0).getStLoction());
						b.setValidity((String) item.get(0).getValidity());
						b.setCatDescription((String) item.get(0).getCatDescription());
						b.setQtyRequired((String) item.get(0).getQtyRequired());
						b.setQtyLot((String) item.get(0).getQtyLot());
						b.setPrimaryNo((String) item.get(0).getPrimaryNo());
						b.setSecondaryNo((String) item.get(0).getSecondaryNo());
						b.setPackCode((String) item.get(0).getPackCode());
						b.setPackQty((String) item.get(0).getPackQty());
						b.setPackingGroup((String) item.get(0).getPackingGroup());
						b.setTotalNoOfPackingGroup((String) item.get(0).getTotalNoOfPackingGroup());
						b.setMixGroup((String) item.get(0).getMixGroup());
						b.setMix((String) item.get(0).getMix());
						b.setBomNo((String) item.get(0).getBomNo());
						b.setCaseMap((String) item.get(0).getCaseMap());
						b.setImages((String) item.get(0).getImages());
						b.setCreatedBy((int) item.get(0).getCreatedBy());
						b.setCreatedDate((Date) item.get(0).getCreatedDate());
						b.setModifiedBy((int) item.get(0).getModifiedBy());
						b.setModifiedDate((Date) item.get(0).getModifiedDate());
						b.setStatus((int) item.get(0).getStatus());
						b.setPendingDate((Date) item.get(0).getPendingDate());
						b.setAckDate((Date) item.get(0).getAckDate());
						b.setReceivedDate((Date) item.get(0).getReceivedDate());
						b.setPackedDate((Date) item.get(0).getPackedDate());
						b.setBarCodeNo(item.get(0).getPartNo().replaceAll("\\s", "")+ item.get(0).getBomId()+"P@"+(i+1));
						b.setBomSlNo(i+1+"-"+item.get(0).getTotalNoOfPackingGroup());
						response.add(b);
					}
					}
				return response;
			}
			
		} catch (DataAccessException e) {

			e.printStackTrace();
		}
		return item;
	}

	@Override
	public ResponseDTO getBomById(PartRequestDTO request) {
		List<Bom> plist = new ArrayList<>();
		List<Bom> dto = new ArrayList<>();
		String getProjectCode = env.getProperty("SELECT_PROJECT_CODE");
		Object[] inputs = new Object[] { request.getKey() };

		ResponseDTO response = new ResponseDTO();
		if (request.getSortByColumn().isEmpty()) {
			request.setSortByColumn("CAST(bomNo as UNSIGNED)");
			request.setSortByMode("asc");
		}else if(request.getSortByColumn().equalsIgnoreCase("bomNo")) {
			request.setSortByColumn("CAST(bomNo as UNSIGNED)");
			
		}else if(request.getSortByColumn().equalsIgnoreCase("partNo")) {
			request.setSortByColumn("part_no");
		}
		String defaultQuery = null;
		if(!request.getSearchValue().isEmpty()) {
			defaultQuery = getSearchDefaultQery(request);
		}else {
			defaultQuery = getDefaultQery(request);
		}
		Integer count = 0;
		try {

			String projectCode = jdbcTemplate.queryForObject(getProjectCode, inputs, String.class);

			dto = jdbcTemplate.query(env.getProperty("SELECT_BOM") + defaultQuery, inputs,
					new BeanPropertyRowMapper<Bom>(Bom.class));
			
			 count = jdbcTemplate.queryForObject(env.getProperty("COUNT_BOM"), inputs, Integer.class);

			dto.forEach(m -> {
				Bom b = new Bom();
				
				b.setBomId((int) m.getBomId());
				b.setLot_ref_no((String) m.getLot_ref_no());
				b.setPartNo((String) m.getPartNo());
				b.setPartDescription((String) m.getPartDescription());
				b.setVersion((String) m.getVersion());
				b.setStLoction((String) m.getStLoction());
				b.setValidity((String) m.getValidity());
				b.setCatDescription((String) m.getCatDescription());
				b.setQtyRequired((String) m.getQtyRequired());
				b.setQtyLot((String) m.getQtyLot());
				b.setPrimaryNo((String) m.getPrimaryNo());
				b.setSecondaryNo((String) m.getSecondaryNo());
				b.setPackCode((String) m.getPackCode());
				b.setPackQty((String) m.getPackQty());
				b.setPackingGroup((String) m.getPackingGroup());
				b.setTotalNoOfPackingGroup((String) m.getTotalNoOfPackingGroup());
				b.setMixGroup((String) m.getMixGroup());
				b.setMix((String) m.getMix());
				b.setBomNo((String) m.getBomNo());
				b.setCaseMap((String) m.getCaseMap());
				b.setImages((String) m.getImages());
				b.setCreatedBy((int) m.getCreatedBy());
				b.setCreatedDate((Date) m.getCreatedDate());
				b.setModifiedBy((int) m.getModifiedBy());
				b.setModifiedDate((Date) m.getModifiedDate());
				b.setStatus((int) m.getStatus());
				b.setPendingDate((Date) m.getPendingDate());
				b.setAckDate((Date) m.getAckDate());
				b.setReceivedDate((Date) m.getReceivedDate());
				b.setPackedDate((Date) m.getPackedDate());
				
				b.setProjectCode(projectCode);
				b.setEnablePartLabel(false);
				b.setEnableCaseReport(false);
				if ((int) m.getPick_label_scan() == 0) {
					b.setLabelStatus("Pending");
					b.setColorCode("#FFFF00");
				} else if ((int) m.getPart_label_scan() > 0
						&& (int) m.getPart_label_scan() < Integer.parseInt(m.getTotalNoOfPackingGroup())) {
					b.setLabelStatus("Work In Progress");
					b.setColorCode("#00FF00");
					b.setEnablePartLabel(true);
				} else if ((int) m.getPick_label_scan() == 2) {

					if ((int) m.getPart_label_scan() == Integer.parseInt(m.getTotalNoOfPackingGroup())) {
						b.setLabelStatus("Packed");
						b.setColorCode("#0000FF");
						b.setEnablePartLabel(true);
						b.setEnableCaseReport(true);
					} else {
						b.setLabelStatus("Received");
						b.setColorCode("#964B00");
						b.setEnablePartLabel(true);
					}

				} else if ((int) m.getPick_label_scan() == 1) {
					b.setLabelStatus("Acknowledge");
					b.setColorCode("#FFA500");
				}

				b.setTotalPartScanned((int) m.getPart_label_scan() + "/" + m.getTotalNoOfPackingGroup());

				
				plist.add(b);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setData(plist);
		response.setTotalElements(count);
		if (!response.getData().isEmpty()) {
			response.setNoData(false);
		}
		return response;
	}

	private String getSearchDefaultQery(PartRequestDTO request) {
		if(!request.getSearchValue().get(0).isEmpty() && !request.getSearchValue().get(1).isEmpty()) {
			return (String) " and part_no like '%"+request.getSearchValue().get(0)+"%' and  bomNo like '%"+request.getSearchValue().get(1)+"%' order by " + request.getSortByColumn();
		}else if(!request.getSearchValue().get(0).isEmpty()) {
			return (String) " and part_no like '%"+request.getSearchValue().get(0)+"%' order by " + request.getSortByColumn();
		}else if(!request.getSearchValue().get(1).isEmpty()) {
			return (String) " and bomNo like '%"+request.getSearchValue().get(1)+"%' order by " + request.getSortByColumn();
		}
		return null;
		
	}

	private String getDefaultQery(PartRequestDTO request) {
		return (String) " order by " + request.getSortByColumn() + " " + request.getSortByMode() + " LIMIT "
				+ request.getLimit() + " OFFSET " + request.getOffset();
	}

	@Override
	public Map<String,byte[]> getImage(String lot_ref_no, String packingGroup) {
		Map<String, byte[]> map = new HashMap<>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", lot_ref_no+"-"+packingGroup);
		Object[] inputs = new Object[] { lot_ref_no+"-"+packingGroup };
		List<ImageModel> dto = jdbcTemplate.query(env.getProperty("SELECT_CASE_IMAGE_NAME"), new String[]{"%"+lot_ref_no+"-"+packingGroup+"%"},
				new BeanPropertyRowMapper<ImageModel>(ImageModel.class));
		
		for(ImageModel im: dto) {
			map.put(im.getName(), im.getPicByte());
		}
		
		return map;
	}

	@Override
	public Bom checkPickLableScanData(String value) {
		Object[] inputs = new Object[] { value };
		Bom id = null;
		try {
			id = jdbcTemplate.queryForObject(env.getProperty("SELECT_BOMID_PICK"), inputs, new BeanPropertyRowMapper<Bom>(Bom.class));
		} catch (DataAccessException e) {
			
		}
		return id;
	}

	@Override
	public Bom checkPartLableScanData(String id) {
		Object[] inputs = new Object[] { id };
		Bom bom = null;
		try {
			bom = jdbcTemplate.queryForObject(env.getProperty("SELECT_BOMID_PART"), inputs, new BeanPropertyRowMapper<Bom>(Bom.class));
		} catch (DataAccessException e) {
			
		}
		return bom;
	}
	
	public Object[] getObject(Bom bom, boolean flag) {
		if(!flag) {
		return new Object[] {
				bom.getLot_ref_no(),
				bom.getPartNo(),
				bom.getPartDescription(),
				bom.getVersion(),
				bom.getStLoction(),
				bom.getValidity(),
				bom.getCatDescription(),
				bom.getQtyRequired(),

				bom.getQtyLot(),
				 bom.getPrimaryNo(),
				 bom.getSecondaryNo(),
				 bom.getPackCode(),

				 bom.getPackQty(),
				 bom.getPackingGroup(),
				 bom.getTotalNoOfPackingGroup(),
				 bom.getMixGroup(),

				 bom.getMix(),
				 bom.getBomNo(),
				 bom.getCaseMap(),
				 bom.getImages(),
				 bom.getCreatedBy()
				
				
     };
		}else {
			return new Object[] {
					bom.getLot_ref_no(),
					bom.getPartNo(),
					bom.getPartDescription(),
					bom.getVersion(),
					bom.getStLoction(),
					bom.getValidity(),
					bom.getCatDescription(),
					bom.getQtyRequired(),

					bom.getQtyLot(),
					 bom.getPrimaryNo(),
					 bom.getSecondaryNo(),
					 bom.getPackCode(),

					 bom.getPackQty(),
					 bom.getPackingGroup(),
					 bom.getTotalNoOfPackingGroup(),
					 bom.getMixGroup(),

					 bom.getMix(),
					 bom.getBomNo(),
					 bom.getCaseMap(),
					 bom.getCreatedBy(),
					
					bom.getBomId()
		};
		}
		
	}

	@Override
	public int itemUpdate(Item request) {
		try {
			if(request.getItem_id()!= 0 && !request.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("UPDATE_ITEM"), getObject(request));
			}else if(request.isDeleteFlag()) {
				return jdbcTemplate.update(env.getProperty("DELETE_ITEM"),  new Object[] {request.getItem_id()});
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	

	private Object[] getObject(Item request) {
		return new Object[] {
				request.getCustomer_code(),
				request.getProject_code(),
				request.getOrg_country_id(),
				request.getPacking_type(),
				request.getLot_size(),
				request.getContainers().toString(),
				
				request.getItem_id()
	};
	}

}
