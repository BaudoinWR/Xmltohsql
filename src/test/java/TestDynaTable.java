import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import fr.woorib.xmltosql.DatabaseLoader;
import junit.framework.Assert;

public class TestDynaTable {
  Logger log = Logger.getLogger(this.getClass().getName());
  static {
    try {
      Class.forName("org.hsqldb.jdbcDriver" );
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

  }
  @Test
  public void testGetSchema() throws SQLException, IOException, SAXException, ParserConfigurationException {
    DatabaseLoader.loadDataSet(this.getClass().getResourceAsStream("/data.xml"));
    try (Connection sa = getConnection();
         PreparedStatement preparedStatement = sa.prepareStatement("select * from test where col1 = 'aa'");
         ResultSet resultSet = preparedStatement.executeQuery()) {
      Assert.assertEquals(1, resultSet.getFetchSize());
      resultSet.next();
      Assert.assertEquals("aa", resultSet.getString("col1"));
      Assert.assertEquals("bjla", resultSet.getString("col2"));
      Assert.assertEquals("nnnk", resultSet.getString("col3"));
      resultSet.close();
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "sa", "");
  }

}
 
