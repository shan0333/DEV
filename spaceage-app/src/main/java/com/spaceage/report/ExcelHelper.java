package com.spaceage.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spaceage.model.Bom;
import com.spaceage.model.SummaryDTO;

public class ExcelHelper {

	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "SNo","STATUS","SCANNED QTY","PART NO","PART DESCRIPTION","CAT DESCRIPTION","QTY REQUIRED","PACK CODE","PACK QTY","PACKING GROUP","MIX GROUP"};
	  static String[] HEADERSUMMARY = { "Packaging Type","Spaceage Lot No","Customer Code","Customer Name","Customer location","Project code","Project Description","Lot Size","Origin Country","Destination Customer name","Destination Country"};
	  static String SHEET = "Tutorials";

	  public static ByteArrayInputStream tutorialsToExcel(List<Bom> bom, SummaryDTO item) {

		 List<SummaryDTO> data= Arrays.asList(item); 
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
    
	      //Summary
	      for (int col = 0; col < HEADERSUMMARY.length; col++) {
	    	  Row summaryRow = sheet.createRow(col);
	    	  summaryRow.createCell(0).setCellValue(HEADERSUMMARY[col]);
//	    	 
	    	  if(col == 0 ) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getType());
	    	  }else if(col == 1) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getLot_ref_no());
	    	  }else if(col == 2) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getCustomer_code());
	    	  }else if(col == 3) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getName());
	    	  }else if(col == 4) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getCustomer_location());
	    	  }else if(col == 5) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getProject_code());
	    	  }else if(col == 6) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getProject_name());
	    	  }else if(col == 7) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getLot_size());
	    	  }else if(col == 8) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getCountry());
	    	  }else if(col == 9) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getDesignation());
	    	  }else if(col == 10) {
	    		  summaryRow.createCell(1).setCellValue(data.get(0).getDestination_location());
	    	  }
	    	 
		  }
	      
	      // Header
	      Row headerRow = sheet.createRow(13);
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }

	      int rowIdx = 14;
	      int count =1;
	      for (Bom b : bom) {
	        Row row = sheet.createRow(rowIdx++);

	        row.createCell(0).setCellValue(count);
	        row.createCell(1).setCellValue(b.getLabelStatus());
	        row.createCell(2).setCellValue(b.getTotalPartScanned());
	        row.createCell(3).setCellValue(b.getPartNo());
	        row.createCell(4).setCellValue(b.getPartDescription());
	        row.createCell(5).setCellValue(b.getCatDescription());
	        row.createCell(6).setCellValue(Integer.parseInt(b.getQtyRequired()));
	        row.createCell(7).setCellValue(b.getPackCode());
	        row.createCell(8).setCellValue(Integer.parseInt(b.getPackQty()));
	        row.createCell(9).setCellValue(b.getPackingGroup());
	        row.createCell(10).setCellValue(Integer.parseInt(b.getMixGroup()));
	        count++;
	      }
	      sheet.autoSizeColumn(0);
	      sheet.autoSizeColumn(1);
	      sheet.autoSizeColumn(2);
	      sheet.autoSizeColumn(3);
	      sheet.autoSizeColumn(4);
	      sheet.autoSizeColumn(5);
	      sheet.autoSizeColumn(6);
	      sheet.autoSizeColumn(7);
	      sheet.autoSizeColumn(8);
	      sheet.autoSizeColumn(9);
	      sheet.autoSizeColumn(10);
	      sheet.autoSizeColumn(11);
	      
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
	}
