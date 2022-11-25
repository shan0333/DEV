package com.spaceage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceage.csv.CSVHelper;
import com.spaceage.model.Bom;
import com.spaceage.model.Country;
import com.spaceage.model.Customer;
import com.spaceage.model.Item;
import com.spaceage.model.Item_masterDTO;
import com.spaceage.model.PackagingType;
import com.spaceage.model.Project;
import com.spaceage.model.RequestDTO;
import com.spaceage.model.ResponseDTO;
import com.spaceage.service.ItemMasterService;

@Repository
public class ItemMasterServiceImpl implements ItemMasterService {

	@Autowired
	Environment env;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(MultipartFile file, long itemId) {
		try {
			List<Bom> fileData = CSVHelper.csvToBom(file.getInputStream(), itemId);

			jdbcTemplate.batchUpdate(env.getProperty("insert_bom"), new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pStmt, int i) throws SQLException {
					Bom bom = fileData.get(i);
					pStmt.setLong(1, bom.getItemMasterId());
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
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ResponseDTO getAllItems(RequestDTO request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getSortByColumn().isEmpty()) {
			request.setSortByColumn("item_id");
		}
		try {
			String defaultQuery = getDefaultQery(request); 
			List<Item_masterDTO> dto = jdbcTemplate.query(env.getProperty("select_all_item")+ defaultQuery,
					new BeanPropertyRowMapper<Item_masterDTO>(Item_masterDTO.class));

			
			response.setData(dto);
			response.setTotalElements(dto.size());
			if(!response.getData().isEmpty()) {
				response.setNoData(false);
			}
			return response;
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return null;
	}

	private String getDefaultQery(RequestDTO request) {
		return (String) " order by " +request.getSortByColumn() 
		+ " "+request.getSortByMode() +" LIMIT "+ request.getLimit()+" OFFSET "+ request.getOffset();
	}

	@Override
	public List<Customer> getCustomer() {
		try {
			List<Customer> customers = jdbcTemplate.query(env.getProperty("select_customer"),
					new BeanPropertyRowMapper<Customer>(Customer.class));

			return customers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Project> getProject() {
		try {
			List<Project> project = jdbcTemplate.query(env.getProperty("select_project"),
					new BeanPropertyRowMapper<Project>(Project.class));

			return project;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public InputStream load() {
		return null;
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
		
		/* FOR POSTGRE*/
		//return (Integer) holder.getKeyList().get(0).get("item_id");
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
	public List<Bom> getBomById(String id) {
		List<Bom> plist = new ArrayList<>();
		try {
			List<Map<String, Object>> bomList = jdbcTemplate.queryForList(env.getProperty("select_bom"),
					Integer.parseInt(id));
			bomList.forEach(m -> {
				Bom b = new Bom();
				b.setBomId((int) m.get("BOM_ID"));
				b.setItemMasterId((int) m.get("item_master_id"));
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
				plist.add(b);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plist;
	}

}
