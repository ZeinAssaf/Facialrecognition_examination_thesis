package com.fr.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fr.services.FrService;

@Path("/image")
public class ImageResource {
	private FrService frService = new FrService();

	// TODO to be removed later this method is just for testing
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "im tested";
	}

	@POST
	@Path("/{API_KEY}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveCapturedFace(@PathParam("API_KEY") String api_key, String code) throws IOException {
		frService.handleImage(api_key, code);
		return Response.status(Status.OK).build();
	}

}
