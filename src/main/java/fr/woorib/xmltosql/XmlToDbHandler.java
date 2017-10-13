package fr.woorib.xmltosql;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XmlToDbHandler extends DefaultHandler {
  private static final Logger log = Logger.getLogger(XmlToDbHandler.class.getName());

  private int depth = 0;
  Map<String, TableCreator> tables = new HashMap<>();
  private TableCreator currentTable = null;
  private LineCreator currentLine = null;
  private String column = null;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    depth++;
    if ("dataset".equalsIgnoreCase(qName)) {
      log.log(Level.FINE, "Found dataset");
    }
    switch (depth) {
      case 2:
        manageTable(qName);
        break;
      case 3:
        manageColumn(qName);
        break;
      default:
        break;
    }
    super.startElement(uri, localName, qName, attributes);
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
    depth--;
    if (depth == 1) {
      currentTable.addLine(currentLine);
      currentLine = null;
      currentTable = null;
    }
    if (depth == 0) {
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
    }
    currentTable = tableCreator;
    currentLine = new LineCreator();
    log.log(Level.FINE, "table = " + qName);
  }
}
 
