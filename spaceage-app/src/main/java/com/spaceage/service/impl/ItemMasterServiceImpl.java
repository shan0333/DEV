package com.spaceage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.spaceage.service.ItemMasterService;

@Repository
public class ItemMasterServiceImpl implements ItemMasterService {
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	public final String INSER_BOM = "insert into tblbom (item_master_id,part_no,"
			+"part_description,"
			+"version,"
			+"st_location,"
			+"validity,"
			+"cat_description,"
			+"qty_required,"
			+"qty_lot,"
			+"primary_no,"
			+"secondary_no,"
			+"pack_code,"
			+"pack_qty,"
			+"packing_group,"
			+"total_no_of_packing_group,"
			+"mix_group,"
			+"mix,"
			+"bomNo,"
			+"case_map,"
			+"images) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public final String INSER_ITEM = "insert into item_master(customer_code,"
			+ "project_code,"
			+ "org_country_id,"
			+ "packing_type,"
			+ "lot_size,"
			+ "customer_login,"
			+ "lot_ref_no,"
			+ "containers) values(?,?,?,?,?,?,?,?)";
	public void save(MultipartFile file, long itemId) {
		try {
			List<Bom> fileData = CSVHelper.csvToBom(file.getInputStream(), itemId);

			jdbcTemplate.batchUpdate(INSER_BOM,new BatchPreparedStatementSetter() {

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
				}

				@Override
				public int getBatchSize() {
					 return fileData.size();
				}});
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@Override
	public List<Item_masterDTO> getAllItems() {
		try {
			String sql = "select i.item_id, i.lot_ref_no, c.customer_code, c.name, c.customer_location, p.project_code, p.project_name, c.designation, i.createdDate, c.country from item_master i join project_master p ON i.project_code  = p.project_id join customer_master c ON i.customer_code  = c.customer_id";

			List<Item_masterDTO> dto = jdbcTemplate.query(
			        sql,
			        new BeanPropertyRowMapper<Item_masterDTO>(Item_masterDTO.class));

			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Customer> getCustomer() {
		try {
			String sql = "select * from customer_master";

			List<Customer> customers = jdbcTemplate.query(
			        sql,
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
			String sql = "select * from project_master";

			List<Project> project = jdbcTemplate.query(
			        sql,
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
				PreparedStatement ps = connection.prepareStatement(INSER_ITEM, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, itemJson.getCustomer_code());
				ps.setLong(2, itemJson.getProject_code());
				ps.setLong(3, itemJson.getOrg_country_id());
				ps.setLong(4, itemJson.getPacking_type());
				ps.setString(5, itemJson.getLot_size());
				ps.setLong(6, itemJson.getCustomer_login());
				ps.setString(7, itemJson.getLot_ref_no());
				ps.setString(8, itemJson.getContainers().toString());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}



	@Override
	public List<PackagingType> getPackagingType() {
		try {
			String sql = "select * from packaging_type";

			List<PackagingType> type = jdbcTemplate.query(
			        sql,
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
			String sql = "select * from country";

			List<Country> country = jdbcTemplate.query(
			        sql,
			        new BeanPropertyRowMapper<Country>(Country.class));

			return country;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
