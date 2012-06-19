package webservice.weather;

import java.rmi.RemoteException;

public interface WeatherService {
	public String getCityWeatherForCountry(String countryname) throws RemoteException;
}
