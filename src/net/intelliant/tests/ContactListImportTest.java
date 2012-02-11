package net.intelliant.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.ByteWrapper;

public class ContactListImportTest extends MailerTests {
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private ByteWrapper getFile(String filePath) throws IOException{
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte []data = new byte[256];
		while(fis.read(data) != -1){
			baos.write(data);
		}
		return new ByteWrapper(baos.toByteArray());
	}

	private String insertIntoImportMapper(GenericValue admin) throws GenericEntityException{
		ImportTests it = new ImportTests();
		Map<String, Object> inputs = it.getCreateImportMapperData(delegator, admin);

		Map<String, Object> inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "middleName");
		inputsEntityColNames.put("2", "lastName");		
		inputsEntityColNames.put("3", "emailAddress");
		inputsEntityColNames.put("4", "phoneNo");
		inputsEntityColNames.put("5", "mobileNo");
		inputsEntityColNames.put("6", "organisationName");
		inputsEntityColNames.put("7", "address1");
		inputsEntityColNames.put("8", "address2");
		inputsEntityColNames.put("9", "postalCode");
		inputsEntityColNames.put("10", "stateName");
		inputsEntityColNames.put("11", "countyName");
		inputsEntityColNames.put("12", "date");

		inputs.put("entityColName", inputsEntityColNames);

		Map<String, Object> inputsimportFileColIdx = UtilMisc.toMap("0", "0");
		inputsimportFileColIdx.put("1", "1");
		inputsimportFileColIdx.put("2", "2");
		inputsimportFileColIdx.put("3", "3");
		inputsimportFileColIdx.put("4", "4");
		inputsimportFileColIdx.put("5", "5");
		inputsimportFileColIdx.put("6", "6");
		inputsimportFileColIdx.put("7", "7");
		inputsimportFileColIdx.put("8", "8");
		inputsimportFileColIdx.put("9", "9");
		inputsimportFileColIdx.put("10", "10");
		inputsimportFileColIdx.put("11", "11");
		inputsimportFileColIdx.put("12", "12");
		inputs.put("importFileColIdx", inputsimportFileColIdx);

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.configureImportMapping", inputs);
		String importMapperId = (String) results.get("importMapperId");

		return importMapperId;
	}
	
	public void testImportList(){
		String ofbizHome = System.getProperty("ofbiz.home");
		if (!ofbizHome.endsWith(File.separator)) {
			ofbizHome += File.separator;
		}
		String tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample4.xls";
		String excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);

		try{
			Map<String, Object> inputData = UtilMisc.toMap("_uploadedFile_fileName", "import_sample4.xls");
			inputData.put("userLogin", admin);
			inputData.put("importMapperId", insertIntoImportMapper(admin));
			inputData.put("contactListId", "10732");
			inputData.put("uploadedFile", getFile(excelFilePath));
			inputData.put("_uploadedFile_contentType", "EXCEL");
			//inputData.put("excelFilePath", excelFilePath);

			Map<String, Object> result = runAndAssertServiceSuccess("mailer.importContactList", inputData);

			System.out.println("Result : "+result);
		}catch (Exception e) {
			Debug.log(e);
			fail(e.getMessage());
		}
	}
}
