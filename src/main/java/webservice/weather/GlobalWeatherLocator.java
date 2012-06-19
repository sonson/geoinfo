/**
 * GlobalWeatherLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.weather;

public class GlobalWeatherLocator extends org.apache.axis.client.Service implements webservice.weather.GlobalWeather {

    public GlobalWeatherLocator() {
    }


    public GlobalWeatherLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GlobalWeatherLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GlobalWeatherSoap
    private java.lang.String GlobalWeatherSoap_address = "http://www.webservicex.net/globalweather.asmx";

    public java.lang.String getGlobalWeatherSoapAddress() {
        return GlobalWeatherSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GlobalWeatherSoapWSDDServiceName = "GlobalWeatherSoap";

    public java.lang.String getGlobalWeatherSoapWSDDServiceName() {
        return GlobalWeatherSoapWSDDServiceName;
    }

    public void setGlobalWeatherSoapWSDDServiceName(java.lang.String name) {
        GlobalWeatherSoapWSDDServiceName = name;
    }

    public webservice.weather.GlobalWeatherSoap getGlobalWeatherSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GlobalWeatherSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGlobalWeatherSoap(endpoint);
    }

    public webservice.weather.GlobalWeatherSoap getGlobalWeatherSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webservice.weather.GlobalWeatherSoapStub _stub = new webservice.weather.GlobalWeatherSoapStub(portAddress, this);
            _stub.setPortName(getGlobalWeatherSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGlobalWeatherSoapEndpointAddress(java.lang.String address) {
        GlobalWeatherSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webservice.weather.GlobalWeatherSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                webservice.weather.GlobalWeatherSoapStub _stub = new webservice.weather.GlobalWeatherSoapStub(new java.net.URL(GlobalWeatherSoap_address), this);
                _stub.setPortName(getGlobalWeatherSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GlobalWeatherSoap".equals(inputPortName)) {
            return getGlobalWeatherSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.webserviceX.NET", "GlobalWeather");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.webserviceX.NET", "GlobalWeatherSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GlobalWeatherSoap".equals(portName)) {
            setGlobalWeatherSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
