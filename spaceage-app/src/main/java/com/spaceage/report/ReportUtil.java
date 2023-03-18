package com.spaceage.report;

public class ReportUtil {

	public static String removeFileExt(String fileName, String contentType) {
		if(fileName.contains(".jpg")) {
			return fileName.replace(".jpg", "");
		}
		return fileName.replace("."+contentType.replace("image/", ""), "");
	}
}
