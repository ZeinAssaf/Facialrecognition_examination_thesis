package helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
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
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

public class FrHelper {

	public BufferedImage decodeImage(String codedFace) throws IOException {
		byte[] codedImage = Base64.getDecoder().decode(codedFace);
		ByteArrayInputStream recievedImage = new ByteArrayInputStream(codedImage);
		BufferedImage image = ImageIO.read(recievedImage);
		return image;
	}

	public String decryptAES(byte[] encrptedImage, SecretKey secretKey, byte[] initializationVector)
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		final String AES_CIPHER = "AES/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(AES_CIPHER);
		IvParameterSpec parameterSpec = new IvParameterSpec(initializationVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
		byte[] s = cipher.doFinal(encrptedImage);
		return new String(s);
	}

	public void CaptureFaces() throws VideoCaptureException {
		Video<MBFImage> video = new VideoCapture(320, 240);
		VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
		display.addVideoListener(new VideoDisplayListener<MBFImage>() {
			public void afterUpdate(VideoDisplay<MBFImage> display) {
			}

			int numImages = 0;

			public void beforeUpdate(MBFImage frame) {
				FaceDetector<KEDetectedFace, FImage> faceDetector = new FKEFaceDetector(60);
				List<KEDetectedFace> faces = faceDetector.detectFaces(Transforms.calculateIntensity(frame));
				if (!faces.isEmpty()&&numImages<100) {

					try {
						AffineAligner aligner = new AffineAligner();
						for (KEDetectedFace face : faces) {
							aligner.align(face);
							FImage faceCapture = face.getFacePatch();
							faceCapture = resize(faceCapture);
							ImageUtilities.write(faceCapture,
									new File("C:/Users/Zein/Desktop/samples/" + numImages + ".png"));
							frame.drawShape(face.getBounds(), RGBColour.RED);
							frame.drawText(Integer.toString(numImages), 150, 150, HersheyFont.ASTROLOGY, 50,
									RGBColour.CYAN);
						}
						numImages++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

	}

	public FImage resize(FImage input) throws IOException {
		BufferedImage inputBuffered = ImageUtilities.createBufferedImage(input);
		BufferedImage output = new BufferedImage(89, 114, inputBuffered.getType());
		Graphics2D g = output.createGraphics();

		g.drawImage(inputBuffered, 0, 0, 89, 114, null);
		g.dispose();
		FImage resizedFace = new FImage(89, 114);
		resizedFace = ImageUtilities.assignBufferedImage(output, resizedFace);
		return resizedFace;

	}

}
