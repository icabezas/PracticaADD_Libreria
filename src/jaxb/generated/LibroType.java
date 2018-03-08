
package jaxb.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

//Probando pushear en nueva branch
/**
 * <p>Clase Java para libroType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="libroType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titulo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="autor" maxOccurs="unbounded" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="categoria" maxOccurs="unbounded" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Policial"/>
 *               &lt;enumeration value="Rom�ntica"/>
 *               &lt;enumeration value="Aventura"/>
 *               &lt;enumeration value="Terror"/>
 *               &lt;enumeration value="Ficci�n/Realidad"/>
 *               &lt;enumeration value="Ciencia Ficci�n"/>
 *               &lt;enumeration value="Investigaci�n"/>
 *               &lt;enumeration value="Biogr�fica"/>
 *               &lt;enumeration value="Infantil"/>
 *               &lt;enumeration value="Autoayuda"/>
 *               &lt;enumeration value="Er�tica"/>
 *               &lt;enumeration value="Hogar"/>
 *               &lt;enumeration value="Enciclopedia/Manual"/>
 *               &lt;enumeration value="Pol�tica"/>
 *               &lt;enumeration value="Econom�a/Marketing"/>
 *               &lt;enumeration value="Sociedad"/>
 *               &lt;enumeration value="Deportes"/>
 *               &lt;enumeration value="Viajes/Cultura"/>
 *               &lt;enumeration value="Varios"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="lenguaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="anyo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}short">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="edicion" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="precio">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ISBN" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "libroType", propOrder = {
    "titulo",
    "autor",
    "categoria",
    "lenguaje",
    "anyo",
    "edicion",
    "precio"
})
public class LibroType {

    @XmlElement(required = true)
    protected String titulo;
    protected List<String> autor;
    protected List<String> categoria;
    protected String lenguaje;
    protected int anyo;
    protected int edicion;
    protected float precio;
    @XmlAttribute(name = "ISBN", required = true)
    protected int isbn;


    public LibroType(String titulo, List<String> autor, List<String> categoria, String lenguaje, int anyo, int edicion, float precio, int isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.lenguaje = lenguaje;
        this.anyo = anyo;
        this.edicion = edicion;
        this.precio = precio;
        this.isbn = isbn;
    }

    public LibroType(int isbn) {
        this.isbn = isbn;
    }

    public LibroType() {

    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Gets the value of the autor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the autor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAutor() {
        if (autor == null) {
            autor = new ArrayList<String>();
        }
        return this.autor;
    }

    /**
     * Gets the value of the categoria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCategoria() {
        if (categoria == null) {
            categoria = new ArrayList<String>();
        }
        return this.categoria;
    }

    /**
     * Obtiene el valor de la propiedad lenguaje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLenguaje() {
        return lenguaje;
    }

    /**
     * Define el valor de la propiedad lenguaje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLenguaje(String value) {
        this.lenguaje = value;
    }

    /**
     * Obtiene el valor de la propiedad anyo.
     * 
     */
    public int getAnyo() {
        return anyo;
    }

    /**
     * Define el valor de la propiedad anyo.
     * 
     */
    public void setAnyo(short value) {
        this.anyo = value;
    }

    /**
     * Obtiene el valor de la propiedad edicion.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public int getEdicion() {
        return edicion;
    }

    /**
     * Define el valor de la propiedad edicion.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setEdicion(Byte value) {
        this.edicion = value;
    }

    /**
     * Obtiene el valor de la propiedad precio.
     * 
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Define el valor de la propiedad precio.
     * 
     */
    public void setPrecio(float value) {
        this.precio = value;
    }

    /**
     * Obtiene el valor de la propiedad isbn.
     * 
     */
    public int getISBN() {
        return isbn;
    }

    /**
     * Define el valor de la propiedad isbn.
     * 
     */
    public void setISBN(int value) {
        this.isbn = value;
    }


    @Override
    public String toString() {
        return "LibroType{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}
