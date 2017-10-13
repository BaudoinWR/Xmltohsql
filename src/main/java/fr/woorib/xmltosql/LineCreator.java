package fr.woorib.xmltosql;

import java.util.HashMap;
import java.util.Map;

public class LineCreator {
  Map<String, String> columns = new HashMap<>();
  public void put(String column, String s) {
    columns.put(column, s);
  }

  public String generateInsert(String tableName) {
    StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
    stringBuilder.append(tableName);
    StringBuilder columns = new StringBuilder("(");
    StringBuilder values = new StringBuilder(" VALUES (");
    for (Map.Entry<String, String> entry : this.columns.entrySet()) {
      columns.append(entry.getKey())
        .append(",");
      values.append("'")
        .append(entry.getValue())
        .append("'")
        .append(",");
    }
    columns.deleteCharAt(columns.length() - 1);
    values.deleteCharAt(values.length() - 1);
    columns.append(")");
    values.append(")");
    return stringBuilder.append(columns).append(values).toString();
  }


  public String generateCreate(String tableName) {
    StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
    stringBuilder.append(tableName)
      .append(" (");
    for(String colonne : this.columns.keySet()) {
      stringBuilder.append(colonne)
        .append(" VARCHAR(255),");
    }
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  @Override
  public String toString() {
    return "LineCreator{" +
      "columns=" + columns +
      '}';
  }

}
 
