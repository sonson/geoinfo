package webservice.weather;

import java.rmi.RemoteException;

public class GlobalWeatherClient implements WeatherService{
	static GlobalWeatherSoap service = new GlobalWeatherSoapProxy();
	
	public String getCityWeatherForCountry(final String country) throws RemoteException{
		String cities = service.getCitiesByCountry(country);
	//	System.out.println(cities);
		String city="";
		char[] tmp = new char[1000];
		tmp = cities.toCharArray();
		for (int i=0;i<tmp.length-4;i++){
			if (tmp[i]=='C' && tmp[i+1]=='i' && tmp[i+2]=='t' && tmp[i+3]=='y' && tmp[i+4]=='>'){
				i+=5;
				while (tmp[i]!='<'){
					// System.out.print(tmp[i]);
					city = city.concat(String.valueOf(tmp[i]));
					i++;
				}
				break;
			}
		}
		// System.out.println("\ncity: "+city);
		String weather = service.getWeather(city,country);
		return weather;
	}
	
	/** Just for testing. */
	public static void main(final String[] args) throws Exception {
		WeatherService service = new GlobalWeatherClient();
		// System.out.println(service.getCityWeatherForCountry("Austria"));
		System.out.println(service.getCityWeatherForCountry("France"));
		System.out.println(service.getCityWeatherForCountry("Italy"));
	}
}
