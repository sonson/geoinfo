package webservice;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import webservice.model.CountryName;
import webservice.model.LatLng;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Path("/country")
public class Country {

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
	
	@GET
	@Path("/{lat}/{lng}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public CountryName name(@PathParam("lat") final String lat, @PathParam("lng") final String lng) {
		double latNumber = Double.parseDouble(lat);
		double lngNumber = Double.parseDouble(lat);
		LatLng latLng = new LatLng(latNumber, lngNumber);
		try {
			String name = namesCache.get(latLng);
			CountryName result = new CountryName();
			result.setName(name);
			return result;
		} catch (ExecutionException e) {
			System.err.println("Error getting name of " + latLng);
			e.printStackTrace();
		}
		
		return null;
	}

	/** Returns a country name from the coordinates. */
	private String latLngToCountryName(final LatLng key) {
		System.out.println("Try to find country name for " + key);
		
		Client  client = Client.create();
		WebResource webResource = client.resource("");
		webResource.path("ID").get(String.class);
		
		return null;
	}
	

}
