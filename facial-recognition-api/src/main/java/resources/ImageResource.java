package resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import services.FrService;

@Path("/face")
public class ImageResource {

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Helloooooo";
	}

	@POST
	@Path("/{API_KEY}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveCapturedFace(@PathParam("API_KEY") String api_key, String code) throws IOException {
		FrService frService = new FrService();
		frService.handleImage(api_key, code);
		return Response.ok().build();
	}

}
