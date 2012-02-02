import net.intelliant.imports.UtilImport;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;

importMapperId = parameters.importMapperId;

importMapperGV = delegator.findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));
mailerImportColumnMapperList = importMapperGV.getRelated("MailerImportColumnMapper");

selectedColumnIndexMap = FastMap.newInstance();
for (GenericValue mailerImportColumnMapper : mailerImportColumnMapperList) {
	selectedColumnIndexMap.putAt(mailerImportColumnMapper.entityColName, mailerImportColumnMapper.importFileColIdx);
}

dataResourceGV = importMapperGV.getRelatedOne("Content").getRelatedOne("DataResource");
filePath = null;
if (UtilValidate.isNotEmpty(dataResourceGV)) {
	filePath = dataResourceGV.get("objectInfo");
}

if (UtilValidate.isNotEmpty(filePath)) {
	entityColumnsToIgnore = UtilMisc.toList("lastUpdatedStamp", "lastUpdatedTxStamp", "createdStamp", "createdTxStamp", "importedOnDateTime", "importedByUserLogin");
	lhsColumns = UtilImport.getEntityColumns(context.nameOfEntity, entityColumnsToIgnore);
	context.put("lhsColumns", lhsColumns);
	
	selectedIndexes = FastList.newInstance();
	for (String colName : lhsColumns) {
		index = selectedColumnIndexMap.get(colName.entityColName);
		selectedIndexes.add((index != null) ? index : -1);
	}
	context.put("selectedIndex", selectedIndexes);
	
	rhsColumns = UtilImport.readExcelIndices(filePath, 0);
	context.put("rhsColumns", rhsColumns);
}
