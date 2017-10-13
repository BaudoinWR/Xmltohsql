package fr.woorib.xmltosql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XmlToDbHandler extends DefaultHandler {
  private static final Logger log = Logger.getLogger(XmlToDbHandler.class.getName());

  private XmlDepth depth = XmlDepth.ZERO;
  Map<String, TableCreator> tables = new HashMap<>();
  List<SchemaCreator> schemas = new ArrayList<>();
  SchemaCreator currentSchema = null;
  private TableCreator currentTable = null;
  private LineCreator currentLine = null;
  private String column = null;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    depth = XmlDepth.values()[(this.depth.ordinal() + 1) % XmlDepth.values().length];
    if ("dataset".equalsIgnoreCase(qName)) {
      log.log(Level.FINE, "Found dataset");
    }
    switch (this.depth) {
      case SCHEMA:
        manageSchema(qName, attributes);
        break;
      case TABLE:
        manageTable(qName);
        break;
      case COLUMN:
        manageColumn(qName);
        break;
      default:
        break;
    }
    super.startElement(uri, localName, qName, attributes);
  }

  private void manageSchema(String qName, Attributes attributes) {
    String name = attributes.getValue("alias");
    if (name != null) {
      qName = name;
    }
    currentSchema = new SchemaCreator(qName);
    schemas.add(currentSchema);
    log.log(Level.FINE, "schema = " + qName);

  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (column != null) {
      String s = new String(ch, start, length);
      currentLine.put(column, s);
      column = null;
    }
    super.characters(ch, start, length);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (depth != XmlDepth.ZERO) {
      depth = XmlDepth.values()[(this.depth.ordinal() - 1) % XmlDepth.values().length];
    }
    if (depth == XmlDepth.SCHEMA) {
      currentTable.addLine(currentLine);
      currentLine = null;
    }
    if (depth == XmlDepth.ROOT) {
      currentTable = null;
      currentSchema = null;
      tables.clear();
      log.log(Level.FINE, tables.toString());
    }
    super.endElement(uri, localName, qName);
  }

  private void manageColumn(String qName) {
    column = qName;
    log.log(Level.FINE, "colonne = " + qName);
  }

  private void manageTable(String qName) {
    TableCreator tableCreator = tables.get(qName);
    if (tableCreator == null) {
      tableCreator = new TableCreator(qName);
      tables.put(qName, tableCreator);
      currentSchema.addTable(tableCreator);
    }
    currentTable = tableCreator;
    currentLine = new LineCreator();
    log.log(Level.FINE, "table = " + qName);
  }
}
 
