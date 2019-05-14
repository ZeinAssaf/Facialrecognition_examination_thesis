package com.fr.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import com.fr.helper.FrHelper;
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveCapturedFace(String code) throws IOException {
		FrHelper helper = new FrHelper();
		BufferedImage image = helper.decodeImage(code);
		FImage image2=new FImage(89, 114);
		image2=ImageUtilities.assignBufferedImage(image, image2);
		ImageUtilities.write(image2, new File("C:/Users/Zein/Desktop/mmmm.png"));
		
		if (frService.handleImage(code)) {
			return Response.status(Status.OK).build();
		}

		return Response.status(Status.BAD_REQUEST).build();

	}

}
