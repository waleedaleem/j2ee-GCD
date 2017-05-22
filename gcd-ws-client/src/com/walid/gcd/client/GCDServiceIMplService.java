
package com.walid.gcd.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "GCDServiceIMplService", targetNamespace = "http://gcd.walid.com/", wsdlLocation = "http://localhost:8080/gcd-wsWeb/gcd?wsdl")
public class GCDServiceIMplService
    extends Service
{

    private final static URL GCDSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException GCDSERVICEIMPLSERVICE_EXCEPTION;
    private final static QName GCDSERVICEIMPLSERVICE_QNAME = new QName("http://gcd.walid.com/", "GCDServiceIMplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/gcd-wsWeb/gcd?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        GCDSERVICEIMPLSERVICE_WSDL_LOCATION = url;
        GCDSERVICEIMPLSERVICE_EXCEPTION = e;
    }

    public GCDServiceIMplService() {
        super(__getWsdlLocation(), GCDSERVICEIMPLSERVICE_QNAME);
    }

    public GCDServiceIMplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), GCDSERVICEIMPLSERVICE_QNAME, features);
    }

    public GCDServiceIMplService(URL wsdlLocation) {
        super(wsdlLocation, GCDSERVICEIMPLSERVICE_QNAME);
    }

    public GCDServiceIMplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, GCDSERVICEIMPLSERVICE_QNAME, features);
    }

    public GCDServiceIMplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GCDServiceIMplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GCDServiceIMpl
     */
    @WebEndpoint(name = "GCDServiceIMplPort")
    public GCDServiceIMpl getGCDServiceIMplPort() {
        return super.getPort(new QName("http://gcd.walid.com/", "GCDServiceIMplPort"), GCDServiceIMpl.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GCDServiceIMpl
     */
    @WebEndpoint(name = "GCDServiceIMplPort")
    public GCDServiceIMpl getGCDServiceIMplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://gcd.walid.com/", "GCDServiceIMplPort"), GCDServiceIMpl.class, features);
    }

    private static URL __getWsdlLocation() {
        if (GCDSERVICEIMPLSERVICE_EXCEPTION!= null) {
            throw GCDSERVICEIMPLSERVICE_EXCEPTION;
        }
        return GCDSERVICEIMPLSERVICE_WSDL_LOCATION;
    }

}
