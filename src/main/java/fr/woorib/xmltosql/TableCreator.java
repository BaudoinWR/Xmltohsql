package fr.woorib.xmltosql; /**
 * Paquet de définition
 **/

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class TableCreator {
  String tableName;
  List<LineCreator> lines = new ArrayList<>();

  public TableCreator(String tableName) {
    this.tableName = tableName;
  }

  public void addLine(LineCreator currentLine) {
    lines.add(currentLine);
  }

  public String generateSQL(String schemaName) {
    String prefix = schemaName == null ? "":schemaName+".";
    String prefixedTableName = prefix + this.tableName;
    StringBuilder stringBuilder = new StringBuilder(lines.get(0).generateCreate(prefixedTableName));
    for (LineCreator line : lines) {
      stringBuilder.append("\n");
      stringBuilder.append(line.generateInsert(prefixedTableName));
    }
    return stringBuilder.toString();
  }
}
 
