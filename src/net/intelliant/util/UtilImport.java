/*
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
 */
package net.intelliant.util;

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
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.model.ModelReader;

public final class UtilImport {
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
				entityColumns.add(UtilMisc.toMap("entityColName", field.getName(), "entityColDesc", fieldDesc, "entityColType", field.getType(), "isNotNull", field.getIsNotNull()));
			}
		}
		return entityColumns;
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

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getActiveColumnMappings(GenericDelegator delegator, String columnMapperId) throws GenericEntityException {
		Map<String, Object> data = FastMap.<String, Object> newInstance();

		EntityCondition conditions = new EntityConditionList(UtilMisc.toList(new EntityExpr("importMapperId", EntityOperator.EQUALS, columnMapperId), new EntityExpr("importFileColIdx", EntityOperator.NOT_EQUAL, "_NA_")), EntityOperator.AND);
		List<GenericValue> columnToIndexMappings = delegator.findByCondition("MailerImportColumnMapper", conditions, null, null);

		for (GenericValue columnToIndexMapping : columnToIndexMappings) {
			data.put(columnToIndexMapping.getString("entityColName"), columnToIndexMapping.getString("importFileColIdx"));
		}
		return data;
	}
}
