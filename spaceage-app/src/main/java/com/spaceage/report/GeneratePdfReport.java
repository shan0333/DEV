package com.spaceage.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.spaceage.model.Bom;
import com.spaceage.model.SummaryDTO;

public class GeneratePdfReport {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream caseReport(List<Bom> part, SummaryDTO summaryDTO, Bom bom, Map<String, byte[]> imgMap) throws MalformedURLException, IOException {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	Document document = new Document();
        document.setMargins(25, 25, 25, 100);

        try {
        	// add header and footer
           // HeaderFooterPageEvent event = new HeaderFooterPageEvent(summaryDTO);
        	Chunk glue = new Chunk(new VerticalPositionMark());
        	Image cusLogo = Image.getInstance("http://spaceagecode.s3-website.us-east-2.amazonaws.com/assets/images/ashok_leyland_logo.png");
        	cusLogo.scaleToFit(70, 70);
        	cusLogo.setPaddingTop(90);
//        	cusLogo.setIndentationLeft(450);
        	
        	Image logo = Image.getInstance("http://spaceagecode.s3-website.us-east-2.amazonaws.com/assets/images/company-logo.jpeg");
        	logo.scaleToFit(100, 100);
        	logo.setIndentationLeft(450);
        	
        	LineSeparator ls = new LineSeparator();
        	
        	Paragraph title1 = new Paragraph();
         	title1.add(new Phrase("ALH2 " + " TO " + summaryDTO.getCustomer_location(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
         	title1.setIndentationLeft(150);
            title1.setPaddingTop(70);
            
            Paragraph p = new Paragraph();
            p.add("");
            p.add(glue);
            p.add("PACKING GROUP: " + bom.getPackingGroup());
            
            Chunk caseDetails = new Chunk("CASE DETAILS");
            caseDetails.setUnderline(0.8f, -1f);
            
            Paragraph p2 = new Paragraph();
            p2.add("NAME: "+ bom.getPackCode());
            p2.add(glue);
            SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            p2.add("CASE PACKED ON: "+ myFormatObj.format(bom.getPackedDate()));
            
            Paragraph p3 = new Paragraph();
            p3.add(""); 
            p3.add(glue);
            if(bom.getNetWeight()!= null) {
            	p3.add("NET WEIGHT (KG): "+bom.getNetWeight());
            }else {
            	p3.add("NET WEIGHT (KG): "+0);
            }
            
            
            Paragraph p4 = new Paragraph();
            p4.add("");
            p4.add(glue);
            if(bom.getNetWeight()!= null) {
            	p4.add("GROSS WEIGHT (KG): "+bom.getGrossWeight());
            }else {
            	p4.add("GROSS WEIGHT (KG): "+0);
            }
            
            
            Paragraph p5 = new Paragraph();
            p5.add("");
            p5.add(glue);
            p5.add("");
            
            Paragraph p6 = new Paragraph();
            p6.add("");
            p6.add(glue);
            p6.add("");
            
            Paragraph p7 = new Paragraph();
            p7.add("Packed By");
            p7.add(glue);
            p7.add("Qc By");
            
            Paragraph p8 = new Paragraph();
            p8.add("(SPACEAGE)");
            p8.add(glue);
            p8.add("(SPACEAGE)");
            
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 2, 4, 10, 2, 5});
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            Font rowFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Sl NO", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("BOM No", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Part Number", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Part Description", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("QTY", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Image", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            int count = 1;
            for (Bom s : part) {
                
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(String.valueOf(count++), rowFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(s.getBomNo(), rowFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(s.getPartNo(), rowFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(s.getPartDescription(), rowFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(s.getPackQty(), rowFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
              
                if(s.getPicByte()!= null) {
                    Image partImage = Image.getInstance(s.getPicByte());
                    partImage.scaleToFit(70, 30);
                    cell = new PdfPCell(partImage, true);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                    }else {
                    	cell = new PdfPCell(new Phrase("-"));
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                
            }

            
            
            PdfWriter.getInstance(document, out);
            //writer.setPageEvent(event);
            document.open();
            for(int i=0; i<Integer.parseInt(bom.getTotalNoOfPackingGroup()); i++) {
            document.add(cusLogo);
            document.add(logo);
            document.add(getTitle(bom, summaryDTO, i+1));
            document.add(Chunk.NEWLINE);
            document.add(ls);
            document.add(Chunk.NEWLINE);
            document.add(title1);
            document.add(p); 
            document.add(caseDetails);
            document.add(getCaseNo(bom, summaryDTO, i+1, glue)); 
            document.add(p2); 
            document.add(p3); 
            document.add(p4); 
            document.add(p5); 
            document.add(table);
            document.add(p6); 
            document.add(p7); 
            document.add(p8); 
            document.newPage();
            }
            
            	
            	Paragraph title = new Paragraph();
            	title.add(new Phrase(bom.getLot_ref_no() + " - "+ summaryDTO.getProject_code() + " - " + " CASE LIST ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15)));
            	title.setIndentationLeft(80);
            	Paragraph p1 = new Paragraph();
            	p1.add("LOT NUMBER: "+ summaryDTO.getLot_ref_no());
                p1.add(glue);
                p1.add("PACKING GROUP: "+ bom.getPackingGroup());
                
                PdfPTable t = new PdfPTable(2);
                t.setWidths(new int[]{100,150});
            	document.add(cusLogo);
                document.add(logo);
                document.add(title);
                document.add(Chunk.NEWLINE);
                document.add(ls);
                document.add(Chunk.NEWLINE);
                document.add(title1); 
                
                document.add(p1);
                document.add(Chunk.NEWLINE);
                Paragraph reportName = new Paragraph();
                reportName.add(new Phrase("Photography Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15)));
                reportName.setIndentationLeft(180);
                document.add(reportName);
                
            if(!imgMap.isEmpty()) {
           	for (Entry<String, byte[]> entry : imgMap.entrySet()) {
            		PdfPCell c = new PdfPCell();
            		Image im = null;
            		
    				try {
    					im = Image.getInstance(Base64.decodeBase64(entry.getValue()));
    					//im.scaleToFit(70, 30);
    				} catch (BadElementException | IOException e) {
    					e.printStackTrace();
    				}
            		
    				c = new PdfPCell(new Phrase(entry.getKey(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //c.setMinimumHeight(50f);
                    t.addCell(c);
                    
                    
                    c = new PdfPCell(im, true);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                   // c.setMinimumHeight(50f);
                    t.addCell(c);
          	};
            	 document.add(Chunk.NEWLINE);
            	 document.add(t);
           }
            document.close();
        
        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
	/*
	 * private static Image getCaseImage(Map<String, byte[]> imgMap, String name) {
	 * Image caseImage = null; try { if(imgMap.get(name)!= null) {
	 * 
	 * caseImage = Image.getInstance(Base64.decodeBase64(imgMap.get(name)));
	 * caseImage.scaleToFit(170, 130); caseImage.setIndentationLeft(150); } } catch
	 * (BadElementException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } catch (MalformedURLException e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); } catch (IOException e1) {
	 * // TODO Auto-generated catch block e1.printStackTrace(); }
	 * 
	 * return caseImage; }
	 */
   
public static Paragraph getTitle(Bom bom, SummaryDTO summaryDTO, int count){
    	Paragraph title = new Paragraph();
    	title.add(new Phrase(bom.getLot_ref_no() + " - "+ summaryDTO.getProject_code() + " - " + " CASE LIST - " + count +" / "+bom.getTotalNoOfPackingGroup(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15)));
    	title.setIndentationLeft(80);
    	return title;
    }
   
   public static Paragraph getCaseNo(Bom bom, SummaryDTO summaryDTO, int count, Chunk glue){
	   Paragraph p1 = new Paragraph();
       p1.add("NUMBER: "+ count +" / "+ bom.getTotalNoOfPackingGroup());
       p1.add(glue);
       p1.add("LOT NUMBER: "+ summaryDTO.getLot_ref_no());
   	return p1;
   }
    
}