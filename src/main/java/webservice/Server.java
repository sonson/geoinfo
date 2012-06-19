package webservice;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

/** Starts the REST-Webserver. */
public class Server {

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(9998).build();
	}
	
	 public static final URI BASE_URI = getBaseURI();
	 
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(final String[] args) throws IllegalArgumentException, NullPointerException, IOException {
		HttpServer httpServer = startServer();
		System.out.println("Server started. Try http://localhost:9998/page/index.html");
		System.in.read();
		httpServer.stop();
		System.out.println("Server stopped.");
	}

	private static HttpServer startServer() throws IllegalArgumentException, NullPointerException, IOException {
		System.out.println("Starting server...");
		ResourceConfig rc = new PackagesResourceConfig("webservice");
		return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
	}

}
