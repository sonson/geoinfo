package webservice;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import webservice.model.CountryName;
import webservice.model.Indicators;
import webservice.model.LatLng;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Path("/country")
public class Country {

	private static final String GEONAMES_USERNAME = "sonson";
		
	/** Cache for the country names. */
	private final LoadingCache<LatLng, String> namesCache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.build(
					new CacheLoader<LatLng, String>() {

						@Override
						public String load(final LatLng key) throws Exception {
							return latLngToCountryName(key);
						}

					});

	/** Returns a country name from the coordinates. */
	private String latLngToCountryName(final LatLng latLng) {		
		Client  client = Client.create();
		WebResource webResource = client.resource("http://api.geonames.org/");
		
		System.out.print("Try to find country name for " + latLng + "...");
		String isoCode = webResource.path("countryCode").
				queryParam("lat", latLng.getLat()) . 
				queryParam("lng", latLng.getLng()) .
				queryParam("username", GEONAMES_USERNAME) .
				get(String.class);
		System.out.println(" found " + isoCode);
		
		// Remove the newline from the ISO-Code:
		isoCode = isoCode.replaceAll("\r\n", "");
		
		return isoCode;
	}
	
	@GET
	@Path("/{lat}/{lng}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public GenericEntity<CountryName> name(@PathParam("lat") final String lat, @PathParam("lng") final String lng) {
		LatLng latLng = new LatLng(lat, lng);
		CountryName result = new CountryName();
		try {
			String name = namesCache.get(latLng);
			result.setName(name);
		} catch (ExecutionException e) {
			result.setError(e.getMessage());
		}

		return new GenericEntity<CountryName>(result) {};
	}

	@GET
	@Path("/{lat}/{lng}/indicators")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Indicators indicators(@PathParam("lat") final String lat, @PathParam("lng") final String lng) {
		LatLng latLng = new LatLng(lat, lng);
		Indicators result = new Indicators();
		
		try {
			String name = namesCache.get(latLng);
			retrieveIndicatorsFromWorldbank(name, result);
		} catch (ExecutionException e) {
			
		}
		return result;
	}

	private void retrieveIndicatorsFromWorldbank(final String country, final Indicators result) {
		Client  client = Client.create();
		WebResource webResource = client.resource("http://api.worldbank.org/");
		
		System.out.print("Try to find indicators for " + country + "...");
		String indi = webResource.path("countries/" + country + "/indicators/SP.DYN.CBRT.IN").
				queryParam("date", "2010"). 
				get(String.class);
		System.out.println(" found " + indi);
	}

	
}
