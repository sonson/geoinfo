package webservice;

import java.io.File;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.common.io.Files;

/** Used to simulate a simple webserver for static files. */
@Path("/page")
public class Page {

	@GET
	@Produces("text/plain")
	public Response hello() {
		return Response.ok("<h1>Hello world2!</h1>", "text/html").build();
	}
	
	@GET
	@Path("/{filename: [a-zA-Z0-9_/.]+}")
	@Produces("text/*")
	public Response html(@PathParam("filename") final String filename) {
		String fullFilename = "/html/" + filename;
		
		URL url = getClass().getResource(fullFilename);
		File f = new File(url.getPath());		
		if (!f.exists()) {
			throw new WebApplicationException(404);
		}
		
		String mimetype = "text/" + Files.getFileExtension(filename);
		return Response.ok(f, mimetype).build();
	}

}
