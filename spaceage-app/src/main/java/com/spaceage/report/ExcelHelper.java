package com.spaceage.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spaceage.model.Bom;
import com.spaceage.model.SummaryDTO;

public class ExcelHelper {

	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "SNo","STATUS","SCANNED QTY","PART NO","PART DESCRIPTION","CAT DESCRIPTION","PRIMARY","QTY REQUIRED","PACK CODE","PACK QTY","PACKING GROUP","MIX GROUP", "PENDING", "ACKNOWLEDGE", "RECEIVED", "PACKED"};
	  static String[] HEADERSUMMARY = { "Packaging Type","Spaceage Lot No","Customer Code","Customer Name","Project code","Project Description","Lot Size","Origin Country","Destination Country"};
	  static String[] SUMMARY = { "Description","Aggregate","Loose Parts","Bulk Parts","Total"};
	  static String SHEET = "Bill Of Material";

	  public static ByteArrayInputStream tutorialsToExcel(List<Bom> bom, SummaryDTO item) {

		 List<SummaryDTO> data= Arrays.asList(item); 
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	      long aggeregate = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Aggeregate") || e.getSecondaryNo().equalsIgnoreCase("Aggeregate")))
	    		    .count();
	      
	      long looseItem = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("loose items") || e.getSecondaryNo().equalsIgnoreCase("loose items")))
	    		    .count();
	      
	      long bulkItems = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Bulk items") || e.getSecondaryNo().equalsIgnoreCase("Bulk items")))
	    		    .count();
	      
	      long aggAck = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Aggeregate") || e.getSecondaryNo().equalsIgnoreCase("Aggeregate")) && e.getLabelStatus().equals("Acknowledge"))
	    		    .count();
	      
	      long loosAck = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("loose items") || e.getSecondaryNo().equalsIgnoreCase("loose items")) && e.getLabelStatus().equals("Acknowledge"))
	    		    .count();
	      
	      long bulkAck = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Bulk items") || e.getSecondaryNo().equalsIgnoreCase("Bulk items")) && e.getLabelStatus().equals("Acknowledge"))
	    		    .count();
	      
	      
	      long aggPen = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Aggeregate") || e.getSecondaryNo().equalsIgnoreCase("Aggeregate")) && e.getLabelStatus().equals("Pending"))
	    		    .count();
	      
	      long loosPen = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("loose items") || e.getSecondaryNo().equalsIgnoreCase("loose items")) && e.getLabelStatus().equals("Pending"))
	    		    .count();
	      
	      long bulkPen = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Bulk items") || e.getSecondaryNo().equalsIgnoreCase("Bulk items")) && e.getLabelStatus().equals("Pending"))
	    		    .count();
	      
	      long aggRec = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Aggeregate") || e.getSecondaryNo().equalsIgnoreCase("Aggeregate")) && e.getLabelStatus().equals("Received"))
	    		    .count();
	      
	      long loosRec = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("loose items") || e.getSecondaryNo().equalsIgnoreCase("loose items")) && e.getLabelStatus().equals("Received"))
	    		    .count();
	      
	      long bulkRec = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Bulk items") || e.getSecondaryNo().equalsIgnoreCase("Bulk items")) && e.getLabelStatus().equals("Received"))
	    		    .count();
	      
	      
	      long aggPac = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Aggeregate")|| e.getSecondaryNo().equalsIgnoreCase("Aggeregate")) && e.getLabelStatus().equals("Packed"))
	    		    .count();
	      
	      long loosPac = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("loose items")|| e.getSecondaryNo().equalsIgnoreCase("loose items")) && e.getLabelStatus().equals("Packed"))
	    		    .count();
	      
	      long bulkPac = bom.stream()
	    		    .filter(e ->  Objects.nonNull(e.getPartNo()) && (e.getPrimaryNo().equalsIgnoreCase("Bulk items") || e.getSecondaryNo().equalsIgnoreCase("Bulk items")) && e.getLabelStatus().equals("Packed"))
	    		    .count();
	      
	      Font headerFont = workbook.createFont();
	      headerFont.setColor(IndexedColors.WHITE.index);
	      headerFont.setFontHeightInPoints((short)13);
	      
	      CellStyle style = workbook.createCellStyle();  
          style.setBorderBottom(BorderStyle.THIN);  
          style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
          style.setBorderRight(BorderStyle.THIN);  
          style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
          style.setBorderLeft(BorderStyle.THIN);  
          style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
          style.setBorderTop(BorderStyle.THIN);  
          style.setTopBorderColor(IndexedColors.BLACK.getIndex());
          style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
          style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
          style.setFont(headerFont);
          style.setAlignment(HorizontalAlignment.CENTER);
          
          CellStyle style1 = workbook.createCellStyle();  
          style1.setBorderBottom(BorderStyle.THIN);  
          style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
          style1.setBorderRight(BorderStyle.THIN);  
          style1.setRightBorderColor(IndexedColors.BLACK.getIndex());  
          style1.setBorderLeft(BorderStyle.THIN);  
          style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
          style1.setBorderTop(BorderStyle.THIN);  
          style1.setTopBorderColor(IndexedColors.BLACK.getIndex());  
	      
	      //Summary
          
	      Row summ = sheet.createRow(2);
	      for (int col = 0; col < SUMMARY.length; col++) {
	        Cell cell = summ.createCell(col+1);
	        cell.setCellValue(SUMMARY[col]);
	        cell.setCellStyle(style);
	      }
	        Cell sumRowCell;
	        Row sumTitle = sheet.createRow(0);
	        sumRowCell = sumTitle.createCell(1);
	        sumRowCell.setCellValue("Material Summary");
	        sumRowCell.setCellStyle(style);
	        
	        Row sumRow = sheet.createRow(7);
	        sumRowCell = sumRow.createCell(1);
	        sumRowCell.setCellValue("Total No of Parts");
	        sumRowCell.setCellStyle(style);
	        sumRowCell = sumRow.createCell(2);
	        sumRowCell.setCellValue(aggeregate);
	        sumRowCell.setCellStyle(style);
	        sumRowCell = sumRow.createCell(3);
	        sumRowCell.setCellValue(looseItem);
	        sumRowCell.setCellStyle(style);
	        sumRowCell = sumRow.createCell(4);
	        sumRowCell.setCellValue(bulkItems);
	        sumRowCell.setCellStyle(style);
	        sumRowCell = sumRow.createCell(5);
	        sumRowCell.setCellValue(aggeregate+looseItem+bulkItems);
	        sumRowCell.setCellStyle(style);
	        
	        
	        
	        Row sumRow1 = sheet.createRow(3);
	        sumRowCell = sumRow1.createCell(1);
	        sumRowCell.setCellValue("Acknowledgement");
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow1.createCell(2);
	        sumRowCell.setCellValue(aggAck);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow1.createCell(3);
	        sumRowCell.setCellValue(loosAck);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow1.createCell(4);
	        sumRowCell.setCellValue(bulkAck);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow1.createCell(5);
	        sumRowCell.setCellValue(aggAck+loosAck+bulkAck);
	        sumRowCell.setCellStyle(style1);
	        
	        Row sumRow2 = sheet.createRow(4);
	        sumRowCell = sumRow2.createCell(1);
	        sumRowCell.setCellValue("Pending");
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow2.createCell(2);
	        sumRowCell.setCellValue(aggPen);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow2.createCell(3);
	        sumRowCell.setCellValue(loosPen);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow2.createCell(4);
	        sumRowCell.setCellValue(bulkPen);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow2.createCell(5);
	        sumRowCell.setCellValue(aggPen+loosPen+bulkPen);
	        sumRowCell.setCellStyle(style1);
	        
	        Row sumRow3 = sheet.createRow(5);
	        sumRowCell = sumRow3.createCell(1);
	        sumRowCell.setCellValue("Received");
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow3.createCell(2);
	        sumRowCell.setCellValue(aggRec);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow3.createCell(3);
	        sumRowCell.setCellValue(loosRec);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow3.createCell(4);
	        sumRowCell.setCellValue(bulkRec);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow3.createCell(5);
	        sumRowCell.setCellValue(aggRec+loosRec+bulkRec);
	        sumRowCell.setCellStyle(style1);
	        
	        
	        Row sumRow4 = sheet.createRow(6);
	        sumRowCell = sumRow4.createCell(1);
	        sumRowCell.setCellValue("Packed");
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow4.createCell(2);
	        sumRowCell.setCellValue(aggPac);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow4.createCell(3);
	        sumRowCell.setCellValue(loosPac);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow4.createCell(4);
	        sumRowCell.setCellValue(bulkPac);
	        sumRowCell.setCellStyle(style1);
	        sumRowCell = sumRow4.createCell(5);
	        sumRowCell.setCellValue(aggPac+loosPac+bulkPac);
	        sumRowCell.setCellStyle(style1);
	        
	      //data
	      for (int col = 0; col < HEADERSUMMARY.length; col++) {
	    	  Row summaryRow = sheet.createRow(10+col);
	    	  sumRowCell = summaryRow.createCell(0);
	    	  sumRowCell.setCellValue(HEADERSUMMARY[col]);
	    	  sumRowCell.setCellStyle(style);
//	    	 
	    	  if(col == 0 ) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getType());
	    	  }else if(col == 1) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getLot_ref_no());
	    	  }else if(col == 2) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getCustomer_code());
	    	  }else if(col == 3) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getName());
				} /*
					 * else if(col == 4) { sumRowCell = summaryRow.createCell(1);
					 * sumRowCell.setCellValue(data.get(0).getCustomer_location()); }
					 */
	    	  else if(col == 4) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getProject_code());
	    	  }else if(col == 5) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getProject_name());
	    	  }else if(col == 6) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getLot_size());
	    	  }else if(col == 7) {
	    		  sumRowCell = summaryRow.createCell(1);
	    		  sumRowCell.setCellValue(data.get(0).getCountry());
			  } /*
					 * else if(col == 9) { sumRowCell = summaryRow.createCell(1);
					 * sumRowCell.setCellValue(data.get(0).getDesignation()); }
					 */
	    	  else if(col == 8) {
				  sumRowCell = summaryRow.createCell(1);
				  sumRowCell.setCellValue(data.get(0).getDestination_location());
			  }
				
	    	  sumRowCell.setCellStyle(style1);
		  }
	      
	     
	      
	      // Header
	      Row headerRow = sheet.createRow(20);
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	        cell.setCellStyle(style);
	      }

	      DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");  
	         
	      int rowIdx = 21;
	      int count =1;
	      for (Bom b : bom) {
	        Row row = sheet.createRow(rowIdx++);
	        
	        row.createCell(0).setCellValue(count);
	        row.createCell(1).setCellValue(b.getLabelStatus());
	        row.createCell(2).setCellValue(b.getTotalPartScanned());
	        row.createCell(3).setCellValue(b.getPartNo());
	        row.createCell(4).setCellValue(b.getPartDescription());
	        row.createCell(5).setCellValue(b.getCatDescription());
	        row.createCell(6).setCellValue(b.getPrimaryNo());
	        row.createCell(7).setCellValue(Integer.parseInt(b.getQtyRequired()));
	        row.createCell(8).setCellValue(b.getPackCode());
	        row.createCell(9).setCellValue(Integer.parseInt(b.getPackQty()));
	        row.createCell(10).setCellValue(b.getPackingGroup());
	        row.createCell(11).setCellValue(Integer.parseInt(b.getMixGroup()));
	     
	        row.createCell(12).setCellValue(b.getPendingDate() !=null ? dateFormat.format(b.getPendingDate()): null);
	        row.createCell(13).setCellValue(b.getAckDate() !=null ? dateFormat.format(b.getAckDate()): null);
	        row.createCell(14).setCellValue(b.getReceivedDate() !=null ? dateFormat.format(b.getReceivedDate()): null);
	        row.createCell(15).setCellValue(b.getPackedDate() !=null ? dateFormat.format(b.getPackedDate()): null);
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
	      sheet.autoSizeColumn(12);
	      sheet.autoSizeColumn(13);
	      sheet.autoSizeColumn(14);
	      sheet.autoSizeColumn(15);
	      
	      
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
	}
