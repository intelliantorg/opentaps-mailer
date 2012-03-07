import net.intelliant.imports.UtilImport;
import org.ofbiz.base.util.UtilMisc;

entityColumnsToIgnore = UtilMisc.toList("lastUpdatedStamp", "lastUpdatedTxStamp", "createdStamp", "createdTxStamp", "importedOnDateTime", "importedByUserLogin");
entityColumns = UtilImport.getEntityColumns(context.nameOfEntity, entityColumnsToIgnore);
context.put("entityColumns", entityColumns);