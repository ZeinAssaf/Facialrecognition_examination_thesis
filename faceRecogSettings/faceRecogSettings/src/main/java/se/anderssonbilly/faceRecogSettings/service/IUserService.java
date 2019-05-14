package se.anderssonbilly.faceRecogSettings.service;

import se.anderssonbilly.faceRecogSettings.dao.UserEntity;

public interface IUserService {
	void save(UserEntity user);

	UserEntity findByUsername(String username);
	
}
