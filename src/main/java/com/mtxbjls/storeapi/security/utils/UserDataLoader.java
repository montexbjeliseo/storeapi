package com.mtxbjls.storeapi.security.utils;

import com.mtxbjls.storeapi.security.dtos.RequestUserDTO;
import com.mtxbjls.storeapi.security.models.Role;
import com.mtxbjls.storeapi.security.repositories.RoleRepository;
import com.mtxbjls.storeapi.security.services.IUserService;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader implements CommandLineRunner { 
    
    private final RoleRepository roleRepository;
    private final IUserService userService;
    
    private final String adminEmail;
    private final String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        loadDefaultRoles();
        loadDefaultUsers();
    }

    private void loadDefaultRoles(){
        try {
            if(!roleRepository.existsByName(Constants.Roles.ADMIN)) {
                roleRepository.save(new Role(Constants.Roles.ADMIN, Constants.Roles.ADMIN_DESCRIPTION));
            }
            if(!roleRepository.existsByName(Constants.Roles.CUSTOMER)) {
                roleRepository.save(new Role(Constants.Roles.CUSTOMER, Constants.Roles.CUSTOMER));
            }
            log.info("DATA LOADER: Roles loaded");
        } catch (Exception e) {
            log.error("DATA LOADER: Error loading roles, message: " + e.getMessage());
        }

    }
    private void loadDefaultUsers() {
        try {
            
            if(adminEmail == null || adminEmail.isBlank()){
                log.warn("admin.email is not defined");
                return;
            }
            if(adminPassword == null || adminPassword.isBlank()){
                log.warn("admin.password is not defined");
                return;
            }
            
            RequestUserDTO requestUserDTO = new RequestUserDTO();
            requestUserDTO.setPassword(adminPassword);
            requestUserDTO.setFirstName("Admin");
            requestUserDTO.setLastName("Admin");
            requestUserDTO.setEmail(adminEmail);
            userService.registerUser(requestUserDTO, Constants.Roles.ADMIN);
            log.info("DATA LOADER: Users loaded");
        } catch (Exception e) {
            log.error("DATA LOADER: Error loading users, message: " + e.getMessage());
        }
    }
    
}
