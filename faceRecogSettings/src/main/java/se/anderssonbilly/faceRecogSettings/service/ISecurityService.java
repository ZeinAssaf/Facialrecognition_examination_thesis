package se.anderssonbilly.faceRecogSettings.service;

public interface ISecurityService {
	boolean isAuthenticated();
	String findLoggedInUsername();
	void autologin(String username, String password);
}
