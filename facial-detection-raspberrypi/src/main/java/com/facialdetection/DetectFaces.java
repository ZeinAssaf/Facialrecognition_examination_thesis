package com.facialdetection;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

public class DetectFaces {
	private static Video<MBFImage> video;
	private static VideoDisplay<MBFImage> display;
	private FaceDetector<KEDetectedFace, FImage> faceDetector;
	private List<KEDetectedFace> faces;
	private AffineAligner aligner;
	private FImage faceCapture;

	public void detectFace() throws VideoCaptureException {
		// Create the video object
		video = new VideoCapture(320, 240);
		// Create the object that will display the video and it will capture the video
		// from the first attached camera
		display = VideoDisplay.createVideoDisplay(video);
		// Listener to listen to the video and capture a face from it if it's found
		display.addVideoListener(new VideoDisplayListener<MBFImage>() {
			public void afterUpdate(VideoDisplay<MBFImage> display) {
			}

			// Here the capturing of a face happens
			public void beforeUpdate(MBFImage frame) {
				faceDetector = new FKEFaceDetector(40);
				faces = faceDetector.detectFaces(Transforms.calculateIntensity(frame));// detect a face from each frame
																						// of the video
				aligner = new AffineAligner(); // the aligner aligns the face in a right position to be recognized later
				
				for (KEDetectedFace face : faces) {
					int index = 0;
					aligner.align(face);
					faceCapture = face.getFacePatch();
					try {
						String path = "C:/Users/Zein/Desktop/capturedFace/";
						String name = index + ".jpg";
						ImageUtilities.write(faceCapture, new File(path + name));
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(faceCapture);
					frame.drawShape(face.getBounds(), RGBColour.RED);
					index++;	
				}

			}
		});

	}

	/**
	 * This method is used to make all the captured images in the same size so they
	 * can be compared
	 *
	 * @param path   is the path to the image
	 * @param name   file name
	 * @param width
	 * @param height
	 */
	public void resizeImage(String path, String name, int width, int height, String filetype) {
		for (int i = 0; i < 100; i++) {
			try {
				BufferedImage input = ImageIO.read(new File(path));
				BufferedImage output = new BufferedImage(width, height, input.getType());
				Graphics2D g = output.createGraphics();
				g.drawImage(input, 0, 0, width, height, null);
				g.dispose();
				ImageIO.write(output, filetype, new File(path + name));

			} catch (Exception e) {
				System.err.println("File not found or currupted, check that teh specified file is an image");
				e.printStackTrace();
			}

		}

	}
}
