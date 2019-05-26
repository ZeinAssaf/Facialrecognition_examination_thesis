package com.facialdetection;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

public class App1 {

    public static String TARGET_URL = "http://localhost:8080/facial-recognition-api/api/face/";
    public static String TARGET_URL_AWS = "http://FaceRec-env.pk2vdd2h3t.eu-north-1.elasticbeanstalk.com/api/face/";

    private static String API_KEY = "123456";

    public static void main(String[] args) {
	System.load("C:/Programs/opencv3/opencv/build/java/x64/opencv_java410.dll");
	FaceHandling faceHandling = new FaceHandling();
	Long delay = 4000L;
	Long start = System.currentTimeMillis();
	List<String> faces = new ArrayList<String>();
	while (true) {
	    Long now = System.currentTimeMillis();
	    faces = faceHandling.detectFace();
	    if (now >= (start + delay)) {
		if (!faces.isEmpty()) {
		    for (String face : faces) {
			Response response = faceHandling.postImage(TARGET_URL_AWS, face, API_KEY);
			System.out.println(response);
		    }
		    faces.clear();
		}

	    }
	}

    }

}
