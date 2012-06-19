/**
 * GlobalWeatherSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.weather;

public interface GlobalWeatherSoap extends java.rmi.Remote {

    /**
     * Get weather report for all major cities around the world.
     */
    public java.lang.String getWeather(java.lang.String cityName, java.lang.String countryName) throws java.rmi.RemoteException;

    /**
     * Get all major cities by country name(full / part).
     */
    public java.lang.String getCitiesByCountry(java.lang.String countryName) throws java.rmi.RemoteException;
}
