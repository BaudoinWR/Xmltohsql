import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.SAXException;
import fr.woorib.xmltosql.XmlDatasetRunner;
import fr.woorib.xmltosql.XmlDatasource;
import junit.framework.Assert;

@RunWith(XmlDatasetRunner.class)
public class TestDynaTable {
  Logger log = Logger.getLogger(this.getClass().getName());

  @Test
  @XmlDatasource(file = "/data.xml")
  public void testSchemaGL() throws SQLException, IOException, SAXException, ParserConfigurationException {
    try (Connection sa = getConnection();
         PreparedStatement preparedStatement = sa.prepareStatement("select * from $GL$.test where col1 = 'aa'");
         ResultSet resultSet = preparedStatement.executeQuery()) {
      Assert.assertEquals(1, resultSet.getFetchSize());
      resultSet.next();
      Assert.assertEquals("aa", resultSet.getString("col1"));
      Assert.assertEquals("bjla", resultSet.getString("col2"));
      Assert.assertEquals("nnnk", resultSet.getString("col3"));
      resultSet.close();
    }
  }

  public void testSchemaGF() throws SQLException, IOException, SAXException, ParserConfigurationException {
    try (Connection sa = getConnection();
         PreparedStatement preparedStatement = sa.prepareStatement("select * from $GF$.testgf where col1 = 'aa'");
         ResultSet resultSet = preparedStatement.executeQuery()) {
      Assert.assertEquals(1, resultSet.getFetchSize());
      resultSet.next();
      Assert.assertEquals("aa", resultSet.getString("col1"));
      Assert.assertEquals("bla", resultSet.getString("col2"));
      Assert.assertEquals("nnk", resultSet.getString("col3"));
      resultSet.close();
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "sa", "");
  }

}
 
