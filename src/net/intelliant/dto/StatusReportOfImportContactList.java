package net.intelliant.dto;

import java.util.Map;

public class StatusReportOfImportContactList{
	int successfulInsertion;
	int failedInsertion;
	
	Map<Integer,String> fullReport;
	
	public StatusReportOfImportContactList(int successfulInsertion, int failedInsertion, Map<Integer, String> fullReport) {
		this.successfulInsertion = successfulInsertion;
		this.failedInsertion = failedInsertion;
		this.fullReport = fullReport;
	}

	public int getSuccessfulInsertion() {
		return successfulInsertion;
	}

	public int getFailedInsertion() {
		return failedInsertion;
	}

	public Map<Integer, String> getFullReport() {
		return fullReport;
	}
}