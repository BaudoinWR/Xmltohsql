/**
 * Paquet de définition
 **/
package fr.woorib.xmltosql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class DatabaseLoader {

  public static void loadDataSet(InputStream xmlDataset) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();
    XmlToDbHandler handler = new XmlToDbHandler();
    saxParser.parse(xmlDataset, handler);
    List<SchemaCreator> schemas = handler.schemas;
    try {
      Connection sa = getConnection();
      for (SchemaCreator schemaCreator : schemas) {
        Scanner stringReader = new Scanner(schemaCreator.toString());
        while (stringReader.hasNextLine()) {
          PreparedStatement preparedStatement = sa.prepareStatement(stringReader.nextLine());
          preparedStatement.execute();
          preparedStatement.close();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to load database", e);
    }
  }


  private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "sa", "");
  }
}
 
