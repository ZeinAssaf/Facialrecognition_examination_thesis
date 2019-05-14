package se.anderssonbilly.faceRecogSettings.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import se.anderssonbilly.faceRecogSettings.dao.RoleEntity;
import se.anderssonbilly.faceRecogSettings.dao.RoleRepository;
import se.anderssonbilly.faceRecogSettings.dao.SettingsEntity;
import se.anderssonbilly.faceRecogSettings.dao.SettingsRepository;
import se.anderssonbilly.faceRecogSettings.dao.UserEntity;
import se.anderssonbilly.faceRecogSettings.dao.UserRepository;


@Service
public class UserServiceImpl implements IUserService{
	
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserEntity user) {
    	HashSet<RoleEntity> userRole = new HashSet<RoleEntity>();
    	
    	userRole.add(roleRepository.findByName("USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRole);
        
        SettingsEntity userSettings = new SettingsEntity();
        userSettings.setApiKey("API_KEY");
        userSettings = settingsRepository.save(userSettings);
        user.setSettings(userSettings);
        
        userRepository.save(user);
        
    }
    
    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
