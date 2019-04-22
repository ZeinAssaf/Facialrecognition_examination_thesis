package com.facialdetection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

//this class will be removed to the server side or deleted
@Provider
@Produces(MediaType.TEXT_PLAIN)
public class ImageToJSON implements MessageBodyWriter<ByteArrayOutputStream>{
	/**
	 * This method checks if this provider does change bufferd images to json or not
	 * @param type
	 * @param genericType
	 * @param annotations
	 * @param mediaType
	 * @return
	 */
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return ByteArrayOutputStream.class.isAssignableFrom(type);
	}
	
	/**
	 * This message is deprecated and the best practice to return -1
	 * 
	 * @param t
	 * @param type
	 * @param genericType
	 * @param annotations
	 * @param mediaType
	 * @return
	 */
	@Override
	public long getSize(ByteArrayOutputStream t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(ByteArrayOutputStream image, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		entityStream.write(image.toByteArray());
		System.out.println("image converted");
	}

}
