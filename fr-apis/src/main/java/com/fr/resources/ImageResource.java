package com.fr.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/image")
public class ImageResource {
	//TODO to be removed later this method is just for testing
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "im tested";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveCapturedFace(String code) throws IOException {
		byte[] codedImage = Base64.getDecoder().decode(code);

		ByteArrayInputStream recievedImage = new ByteArrayInputStream(codedImage);
		BufferedImage image = ImageIO.read(recievedImage);
		if (codedImage.length > 0) {
			ImageIO.write(image, "png", new File("path to write the recieved image"));
			return Response.status(Status.CREATED).build();
		} else {

			return Response.status(Status.BAD_REQUEST).build();
		}

	}

}
