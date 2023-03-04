package com.spaceage.report;

public class ReportUtil {

	public static String removeFileExt(String fileName, String contentType) {
		
		return fileName.replace("."+contentType.replace("image/", ""), "");
	}
}
