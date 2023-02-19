package com.spaceage.service.impl;

import java.io.ByteArrayInputStream;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.spaceage.model.Item;
import com.spaceage.model.Item_masterDTO;
import com.spaceage.model.PackagingType;
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

			jdbcTemplate.batchUpdate(env.getProperty("insert_bom"), new BatchPreparedStatementSetter() {

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
				dto = jdbcTemplate.query(env.getProperty("select_all_item") + defaultQuery,
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
				PreparedStatement ps = connection.prepareStatement(env.getProperty("insert_item"),
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
		
        Object[] inputs = new Object[] {id};
        try {
        String projectCode = jdbcTemplate.queryForObject(getProjectCode, inputs, String.class);
        
		
		
			List<Map<String, Object>> bomList;
			if(group != null) {
				query = query.concat(" and packing_group =?");
				bomList = jdbcTemplate.queryForList(query, id, group);
			}else {
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
				b.setStatus((boolean) m.get("Status"));
				b.setPendingDate((Date)m.get("pendingDate"));
				b.setAckDate((Date)m.get("ackDate"));
				b.setReceivedDate((Date)m.get("receivedDate"));
				b.setPackedDate((Date)m.get("packedDate"));
				
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
					}else {	
						b.setLabelStatus("Received");
						b.setColorCode("#964B00");
						b.setEnablePartLabel(true);
					}	
//				} else if ((int) m.get("part_label_scan") == Integer.parseInt(b.getTotalNoOfPackingGroup())) {
//					b.setLabelStatus("Packed");
//					b.setColorCode("#0000FF");
//					b.setEnablePartLabel(true);
//					b.setEnableCaseReport(true);
				} else if ((int) m.get("pick_label_scan") == 1) {
					b.setLabelStatus("Acknowledge");
					b.setColorCode("#FFA500");
				}
				//b.setEnablePartLabel((int) m.get("pick_label_scan") > 0);
				b.setTotalPartScanned((int) m.get("part_label_scan") + "/" + b.getTotalNoOfPackingGroup());
				plist.add(b);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

	@Override
	public void createBom(Bom bom) {
		// TODO Auto-generated method stub

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
			summary = (SummaryDTO) jdbcTemplate.queryForObject(env.getProperty("select_itemById"), new Object[] { id },
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
		
		int ca =0;
		try {
			ca = (int) jdbcTemplate.queryForObject(env.getProperty("SELECT_CASE_ID"), new Object[] { c.getLot_ref_no(), c.getBomNo(), c.getPackingGroup() }, Integer.class);
			 		
		} catch (Exception e) {
			
	  } 
		
		try {
		if(ca ==0) {
	
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
		}else {
			jdbcTemplate.update("UPDATE case_weight SET netweight = ?, grossweight = ?  WHERE lot_ref_no=? and bomno=? and packinggroup=? ", new Object[] {c.getNetWeight(), c.getGrossWeight(),  c.getLot_ref_no(), c.getBomNo(), c.getPackingGroup()});
			
		}
		} catch (Exception e) {
			e.printStackTrace();
	  }
	}

	@Override
	public Case getCase(Bom c) {
		
		return (Case) jdbcTemplate.queryForObject(env.getProperty("SELECT_CASE_ID"), new Object[] { c.getLot_ref_no(), c.getBomNo(), c.getPackingGroup() },
		 		new BeanPropertyRowMapper(Case.class));
	}

}
