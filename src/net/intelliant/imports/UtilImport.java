package net.intelliant.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import net.intelliant.imports.bean.EntityFieldStatus;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.model.ModelReader;

public class UtilImport {
	private UtilImport() {
	}

	public static List<Map<String, Object>> getEntityColumns(String entityName, List<String> entityColumnsToIgnore) throws GenericEntityException {
		if (UtilValidate.isEmpty(entityName)) {
			return null;
		}
		if (UtilValidate.isEmpty(entityColumnsToIgnore)) {
			entityColumnsToIgnore = new ArrayList<String>();
		}
		ModelReader reader = ModelReader.getModelReader("default");
		ModelEntity modelEntity = reader.getModelEntity(entityName);
		Iterator<ModelField> fieldIterator = modelEntity.getFieldsIterator();
		List<Map<String, Object>> entityColumns = new ArrayList<Map<String, Object>>();
		while (fieldIterator.hasNext()) {
			ModelField field = fieldIterator.next();
			String fieldDesc = field.getDescription();

			if (UtilValidate.isEmpty(fieldDesc)) {
				fieldDesc = field.getName();
			}
			if (!entityColumnsToIgnore.contains(field.getName()) && !field.getIsPk()) {
				entityColumns.add(UtilMisc.toMap("entityColName", field.getName(), "entityColDesc", fieldDesc,"entityColType",field.getType(), "isNotNull", field.getIsNotNull()));
			}
		}
		return entityColumns;
	}
	public static Map<String,EntityFieldStatus> getEntityColumnsMap(String entityName, List<String> entityColumnsToIgnore) throws GenericEntityException{
		List<Map<String,Object>> columns = getEntityColumns(entityName, entityColumnsToIgnore);
		Map<String,EntityFieldStatus> columnTypes = new HashMap<String, EntityFieldStatus>();

		for(Map<String, Object> data : columns){
			String colName = (String) data.get("entityColName");
			columnTypes.put(colName, new EntityFieldStatus(colName, (String) data.get("entityColType"), ((Boolean)data.get("isNotNull")).booleanValue()));			
		}

		return columnTypes;
	}

	public static List<String> readExcelFirstRow(String excelFilePath, boolean isFirstRowHeader, int sheetIndex) throws FileNotFoundException, IOException {
		List<String> columnIndices = new ArrayList<String>();
		File file = new File(excelFilePath);
		if (file != null && file.canRead()) {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			// HSSFSheet sheet = wb.getSheet(wb.getActiveSheetIndex());
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			if (sheet != null) {
				HSSFRow firstRow = sheet.getRow(sheet.getFirstRowNum());
				if (firstRow != null) {
					firstRow.getPhysicalNumberOfCells();
					Iterator<?> cells = firstRow.cellIterator();
					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						if (isFirstRowHeader) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								columnIndices.add(cell.toString());
							} else {
								columnIndices.add("N/A - " + cell.getCellNum()); 
							}
						} else {
							columnIndices.add(String.valueOf(cell.getCellNum()));
						}
					}
				}
			}
		}		
		return columnIndices;
	}
	
	public static List<String> readExcelHeaderIndices(String excelFilePath, int sheetIndex) throws FileNotFoundException, IOException {
		return readExcelFirstRow(excelFilePath, false, sheetIndex);
	}

	public static List<String> readExcelHeaderValues(String excelFilePath, int sheetIndex) throws FileNotFoundException, IOException {
		return readExcelFirstRow(excelFilePath, true, sheetIndex);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getColumnMappings(GenericDelegator delegator, String columnMapperId) throws GenericEntityException {
		Map<String, Object> data = FastMap.<String, Object> newInstance();
		List<GenericValue> columnToIndexMappings = delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", columnMapperId));
		for (GenericValue columnToIndexMapping : columnToIndexMappings) {
			data.put(columnToIndexMapping.getString("entityColName"), columnToIndexMapping.getString("importFileColIdx"));
		}
		return data;
	}
}
