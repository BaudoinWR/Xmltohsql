/**
 * Paquet de définition
 **/
package fr.woorib.xmltosql;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class SchemaCreator {
  private String schemaName;
  private List<TableCreator> tables = new ArrayList<>();

  public SchemaCreator(String schemaName) {
    this.schemaName = schemaName;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("CREATE SCHEMA ");
    stringBuilder.append(schemaName)
      .append(" AUTHORIZATION DBA");
    for (TableCreator table : tables) {
      stringBuilder.append("\n")
        .append(table.generateSQL(schemaName));
    }
    return stringBuilder.toString();
  }

  public void addTable(TableCreator currentTable) {
    tables.add(currentTable);
  }
}
 
