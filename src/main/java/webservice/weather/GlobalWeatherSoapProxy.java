package webservice.weather;

public class GlobalWeatherSoapProxy implements webservice.weather.GlobalWeatherSoap {
  private String _endpoint = null;
  private webservice.weather.GlobalWeatherSoap globalWeatherSoap = null;
  
  public GlobalWeatherSoapProxy() {
    _initGlobalWeatherSoapProxy();
  }
  
  public GlobalWeatherSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initGlobalWeatherSoapProxy();
  }
  
  private void _initGlobalWeatherSoapProxy() {
    try {
      globalWeatherSoap = (new webservice.weather.GlobalWeatherLocator()).getGlobalWeatherSoap();
      if (globalWeatherSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)globalWeatherSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)globalWeatherSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (globalWeatherSoap != null)
      ((javax.xml.rpc.Stub)globalWeatherSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public webservice.weather.GlobalWeatherSoap getGlobalWeatherSoap() {
    if (globalWeatherSoap == null)
      _initGlobalWeatherSoapProxy();
    return globalWeatherSoap;
  }
  
  public java.lang.String getWeather(java.lang.String cityName, java.lang.String countryName) throws java.rmi.RemoteException{
    if (globalWeatherSoap == null)
      _initGlobalWeatherSoapProxy();
    return globalWeatherSoap.getWeather(cityName, countryName);
  }
  
  public java.lang.String getCitiesByCountry(java.lang.String countryName) throws java.rmi.RemoteException{
    if (globalWeatherSoap == null)
      _initGlobalWeatherSoapProxy();
    return globalWeatherSoap.getCitiesByCountry(countryName);
  }
  
  
}