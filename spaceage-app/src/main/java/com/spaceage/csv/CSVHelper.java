package com.spaceage.csv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.spaceage.model.Bom;


public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

  public static boolean hasCSVFormat(MultipartFile file) {
 System.out.println(file.getContentType());
    if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }

  public static List<Bom> csvToBom(InputStream is, long itemId) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Bom> bomList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  Bom bom = new Bom(
    		  itemId,	  
              csvRecord.get("PART_NO"),
              csvRecord.get("PART_DESCRIPTION"),
              csvRecord.get("Version"),
              csvRecord.get("ST_ LOCATION"),
              csvRecord.get("VALIDITY"),
              csvRecord.get("CAT Description"),
              csvRecord.get("QTY REQUIRED"),
              csvRecord.get("Qty / Lot"),
              csvRecord.get("Primary #"),
              csvRecord.get("Secondary #"),
              csvRecord.get("Pack Code"),
              csvRecord.get("Pack Qty"),
              csvRecord.get("Packing Group"),
              csvRecord.get("Total No of Packing Group"),
              csvRecord.get("Mix Group"),
              csvRecord.get("Mix"),
              csvRecord.get("Bom.no"),
              csvRecord.get("Case_map"),
              csvRecord.get("Images")
            );

    	  bomList.add(bom);
      }

      return bomList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream tutorialsToCSV(List<Bom> developerTutorialList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Bom developerTutorial : developerTutorialList) {
        List<String> data = Arrays.asList(
//              String.valueOf(developerTutorial.getId()),
//              developerTutorial.getTitle(),
//              developerTutorial.getDescription(),
//              String.valueOf(developerTutorial.isPublished())
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
}
