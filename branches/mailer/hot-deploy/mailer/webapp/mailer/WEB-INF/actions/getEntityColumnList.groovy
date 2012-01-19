import net.intelliant.imports.UtilImport
import org.ofbiz.base.util.UtilMisc

entityColumnsToIgnore = UtilMisc.toList("lastUpdatedStamp", "lastUpdatedTxStamp", "createdStamp", "createdTxStamp", "importedOnDateTime", "importedByUserLogin");
entityColumns = UtilImport.getEntityColumns(context.nameOfEntity, entityColumnsToIgnore);
//System.out.println("Entity Columns1 : "+entityColumns);
context.put("entityColumns", entityColumns);