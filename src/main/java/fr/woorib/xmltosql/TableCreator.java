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

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(lines.get(0).generateCreate(tableName));
    for (LineCreator line : lines) {
      stringBuilder.append("\n");
      stringBuilder.append(line.generateInsert(tableName));
    }
    return stringBuilder.toString();
  }
}
 
