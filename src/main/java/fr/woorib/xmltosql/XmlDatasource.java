/**
 * Paquet de définition
 **/
package fr.woorib.xmltosql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: Merci de donner une description du service rendu par cette interface
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlDatasource {
  String file();
}
 
