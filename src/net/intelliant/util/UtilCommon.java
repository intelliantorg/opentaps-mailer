package net.intelliant.util;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;

public final class UtilCommon {
	private static final String module = UtilCommon.class.getName();
	private UtilCommon() {}
	
	public static String parseHtmlAndGenerateCompressedImages(String html) {
		if (UtilValidate.isEmpty(html)) {
			return html;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements jpgs = doc.select("img[src~=(?i)\\.(jpg|jpeg)]");
		if (jpgs != null && jpgs.size() > 0) {
			for (Element jpg : jpgs) {
				if (Debug.infoOn()) {
					Debug.logInfo("Current image >> " + jpg.attr("src"), module);
				}
			}
		}
		return html;
	}

	/**
	 * Gets the path for uploaded files.
	 * 
	 * @return a <code>String</code> value
	 */
	public static String getUploadPath() {
		return System.getProperty("user.dir") + File.separatorChar + "runtime" + File.separatorChar + "data" + File.separatorChar;
	}  
}
