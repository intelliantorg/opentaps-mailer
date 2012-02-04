package net.intelliant.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

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
				entityColumns.add(UtilMisc.toMap("entityColName", field.getName(), "entityColDesc", fieldDesc));
			}
		}
		return entityColumns;
	}

	public static List<Integer> readExcelIndices(String excelFilePath, int sheetIndex) throws FileNotFoundException, IOException {
		List<Integer> columnIndices = new ArrayList<Integer>();
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
						columnIndices.add(Integer.valueOf(cell.getCellNum()));
					}
				}
			}
		}
		return columnIndices;
	}

	public static List<String> readExcelHeaders(String excelFilePath, int sheetIndex) throws FileNotFoundException, IOException {
		List<String> columnIndices = new ArrayList<String>();
		File file = new File(excelFilePath);
		if (file != null && file.canRead()) {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);

			if (sheet != null) {
				HSSFRow firstRow = sheet.getRow(sheet.getFirstRowNum());
				if (firstRow != null) {
					Iterator<?> cells = firstRow.cellIterator();
					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							columnIndices.add(cell.toString());
						} else {
							columnIndices.add("N/A - "+Integer.valueOf(cell.getCellNum())); 
						}
					}
				}
			}
		}
		return columnIndices;
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
