
package jaxb.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jaxb.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Libreria_QNAME = new QName("", "libreria");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jaxb.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LibreriaType }
     * 
     */
    public LibreriaType createLibreriaType() {
        return new LibreriaType();
    }

    /**
     * Create an instance of {@link LibroType }
     * 
     */
    public LibroType createLibroType() {
        return new LibroType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibreriaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "libreria")
    public JAXBElement<LibreriaType> createLibreria(LibreriaType value) {
        return new JAXBElement<LibreriaType>(_Libreria_QNAME, LibreriaType.class, null, value);
    }

}
