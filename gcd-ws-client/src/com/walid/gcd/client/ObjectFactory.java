
package com.walid.gcd.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.walid.gcd.client package. 
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

    private final static QName _GcdResponse_QNAME = new QName("http://gcd.walid.com/", "gcdResponse");
    private final static QName _SumGCDsResponse_QNAME = new QName("http://gcd.walid.com/", "sumGCDsResponse");
    private final static QName _Gcd_QNAME = new QName("http://gcd.walid.com/", "gcd");
    private final static QName _ListGCDs_QNAME = new QName("http://gcd.walid.com/", "listGCDs");
    private final static QName _SumGCDs_QNAME = new QName("http://gcd.walid.com/", "sumGCDs");
    private final static QName _ListGCDsResponse_QNAME = new QName("http://gcd.walid.com/", "listGCDsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.walid.gcd.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SumGCDs }
     * 
     */
    public SumGCDs createSumGCDs() {
        return new SumGCDs();
    }

    /**
     * Create an instance of {@link ListGCDsResponse }
     * 
     */
    public ListGCDsResponse createListGCDsResponse() {
        return new ListGCDsResponse();
    }

    /**
     * Create an instance of {@link ListGCDs }
     * 
     */
    public ListGCDs createListGCDs() {
        return new ListGCDs();
    }

    /**
     * Create an instance of {@link Gcd }
     * 
     */
    public Gcd createGcd() {
        return new Gcd();
    }

    /**
     * Create an instance of {@link GcdResponse }
     * 
     */
    public GcdResponse createGcdResponse() {
        return new GcdResponse();
    }

    /**
     * Create an instance of {@link SumGCDsResponse }
     * 
     */
    public SumGCDsResponse createSumGCDsResponse() {
        return new SumGCDsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GcdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "gcdResponse")
    public JAXBElement<GcdResponse> createGcdResponse(GcdResponse value) {
        return new JAXBElement<GcdResponse>(_GcdResponse_QNAME, GcdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SumGCDsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "sumGCDsResponse")
    public JAXBElement<SumGCDsResponse> createSumGCDsResponse(SumGCDsResponse value) {
        return new JAXBElement<SumGCDsResponse>(_SumGCDsResponse_QNAME, SumGCDsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Gcd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "gcd")
    public JAXBElement<Gcd> createGcd(Gcd value) {
        return new JAXBElement<Gcd>(_Gcd_QNAME, Gcd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListGCDs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "listGCDs")
    public JAXBElement<ListGCDs> createListGCDs(ListGCDs value) {
        return new JAXBElement<ListGCDs>(_ListGCDs_QNAME, ListGCDs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SumGCDs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "sumGCDs")
    public JAXBElement<SumGCDs> createSumGCDs(SumGCDs value) {
        return new JAXBElement<SumGCDs>(_SumGCDs_QNAME, SumGCDs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListGCDsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gcd.walid.com/", name = "listGCDsResponse")
    public JAXBElement<ListGCDsResponse> createListGCDsResponse(ListGCDsResponse value) {
        return new JAXBElement<ListGCDsResponse>(_ListGCDsResponse_QNAME, ListGCDsResponse.class, null, value);
    }

}
