/**
 * Paquet de définition
 **/
package fr.woorib.xmltosql;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.xml.sax.SAXException;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 **/
public class XmlDatasetRunner extends BlockJUnit4ClassRunner {
  public XmlDatasetRunner(Class<?> klass) throws InitializationError, IOException, SAXException, ParserConfigurationException {
    super(klass);
    XmlDatasource annotation = klass.getAnnotation(XmlDatasource.class);
    loadDatasource(annotation);
  }

  private void loadDatasource(XmlDatasource annotation) throws ParserConfigurationException, SAXException, IOException {
    if (annotation != null) {
      try {
        Class.forName("org.hsqldb.jdbcDriver" );
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      InputStream resourceAsStream = XmlDatasetRunner.class.getResourceAsStream(annotation.file());
      DatabaseLoader.loadDataSet(resourceAsStream);
    }
  }

  @Override
  protected void runChild(FrameworkMethod method, RunNotifier notifier) {
    XmlDatasource annotation = method.getAnnotation(XmlDatasource.class);
    try {
      loadDatasource(annotation);
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    catch (SAXException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    super.runChild(method, notifier);
  }
}
