package webservice;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import webservice.model.CountryName;
import webservice.model.Indicators;
import webservice.model.LatLng;
import webservice.weather.GlobalWeatherClient;
import webservice.weather.WeatherService;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Path("/country")
public class Country {

	private static final String GEONAMES_USERNAME = "sonson";

	private final ObjectMapper mapper = new ObjectMapper(); // Jackson JSON
															// Parser

	/** Cache for the country names. */
	private final LoadingCache<LatLng, CountryName> namesCache = CacheBuilder
			.newBuilder().maximumSize(1000)
			.build(new CacheLoader<LatLng, CountryName>() {

				@Override
				public CountryName load(final LatLng key) throws Exception {
					return latLngToCountryName(key);
				}

			});

	/** Returns a country name from the coordinates. */
	private CountryName latLngToCountryName(final LatLng latLng) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://api.geonames.org/");

		System.out.print("Try to find country name for " + latLng + "...");
		String json = webResource.path("countrySubdivisionJSON")
				.queryParam("lat", latLng.getLat())
				.queryParam("lng", latLng.getLng())
				.queryParam("username", GEONAMES_USERNAME).get(String.class);

		CountryName result = new CountryName();
		try {
			JsonNode root = mapper.readTree(json);
			result.setName(root.get("countryName").asText());
			result.setIso(root.get("countryCode").asText());
			System.out.println(" found: " + result);
		} catch (Exception e) {
			System.err.println("JSON error: " + e.getMessage());
		}

		return result;
	}

	@GET
	@Path("/{lat}/{lng}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public GenericEntity<CountryName> name(@PathParam("lat") final String lat,
			@PathParam("lng") final String lng) {
		LatLng latLng = new LatLng(lat, lng);
		CountryName result = new CountryName();
		try {
			String name = namesCache.get(latLng).getName();
			result.setName(name);
		} catch (ExecutionException e) {
			result.setError(e.getMessage());
		}

		return new GenericEntity<CountryName>(result) {
		};
	}

	@GET
	@Path("/{lat}/{lng}/weather")
	@Produces(MediaType.APPLICATION_XML)
	public String weather(@PathParam("lat") final String lat,
			@PathParam("lng") final String lng) {
		LatLng latLng = new LatLng(lat, lng);
		WeatherService service = new GlobalWeatherClient();
		String result = "Weather error.";

		try {
			String name = namesCache.get(latLng).getName();
			System.out.print("Try to find weather for " + name);
			result = service.getCityWeatherForCountry(name);
			System.out.println(". Weather found.");
		} catch (Exception e) {
			System.out.println("Weather Error: " + e.getMessage());
		}
		return result;
	}

	@GET
	@Path("/{lat}/{lng}/indicators")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Indicators indicators(@PathParam("lat") final String lat,
			@PathParam("lng") final String lng) {
		LatLng latLng = new LatLng(lat, lng);
		Indicators result = new Indicators();

		try {
			String iso = namesCache.get(latLng).getIso();
			setIndicatorsFromWorldbank(iso, result);
		} catch (Exception e) {
			System.out.println("Error Indicator: " + e.getMessage());
		}
		return result;
	}

	/**
	 * Sets the values of the result-Indicator-object with data from the
	 * Worldbank.
	 */
	private void setIndicatorsFromWorldbank(final String country,
			final Indicators result) throws JsonProcessingException,
			IOException {
		result.setBirthRate(retrieveIndicatorFromWorldbank(country,
				"SP.DYN.CBRT.IN"));
		result.setDeathRate(retrieveIndicatorFromWorldbank(country,
				"SP.DYN.CDRT.IN"));
		result.setLifeExpectancyFemale(retrieveIndicatorFromWorldbank(country,
				"SP.DYN.LE00.FE.IN"));
		result.setLifeExpectancyMale(retrieveIndicatorFromWorldbank(country,
				"SP.DYN.LE00.MA.IN"));
	}

	/**
	 * Retrieves the value of an indicator from the Worldbank API.
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private String retrieveIndicatorFromWorldbank(final String country,
			final String indicatorName) throws JsonProcessingException,
			IOException {
		Client client = Client.create();
		WebResource webResource = client.resource("http://api.worldbank.org/");

		System.out.println("Try to find Indicator " + indicatorName
				+ " for country " + country + "...");
		String json = webResource
				.path("countries/" + country + "/indicators/" + indicatorName)
				.queryParam("format", "json").queryParam("date", "2010")
				.get(String.class);
		return getValueFromWorldbankJson(json);
	}

	/** Parse the JSON from the worldbank and returns just the value as String. */
	private String getValueFromWorldbankJson(final String json)
			throws JsonProcessingException, IOException {
		JsonNode root = mapper.readTree(json);
		JsonNode data = root.get(1).get(0);
		JsonNode value = data.get("value");
		return value.asText();
	}
}
