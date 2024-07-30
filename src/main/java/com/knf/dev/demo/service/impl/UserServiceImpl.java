package com.knf.dev.demo.service.impl;


import com.knf.dev.demo.dto.UserCreatingDto;
import com.knf.dev.demo.dto.UserRegistrationDto;
import com.knf.dev.demo.entity.Role;
import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.repository.UserRepository;
import com.knf.dev.demo.service.SpecService;
import com.knf.dev.demo.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;
   private BCryptPasswordEncoder passwordEncoder;
   private SpecService specService;
   public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, SpecService specService) {
      super();
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
      this.specService = specService;
   }

   @Override
   public User findUserById(long id) {
      return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
   }

   @Override
   public void updateUser(long id, User user) {
      User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      existingUser.setFirstName(user.getFirstName());
      existingUser.setLastName(user.getLastName());
      existingUser.setEmail(user.getEmail());
      existingUser.setRoles(user.getRoles());
//      existingUser.setPassword(user.getPassword());
      userRepository.save(existingUser);
   }

   @Override
   public User save(UserRegistrationDto registrationDto) {

      var user = new User(registrationDto.getFirstName(),
              registrationDto.getLastName(),
              registrationDto.getEmail(),
              passwordEncoder.encode(registrationDto
                      .getPassword()),
              Arrays.asList(new Role("ROLE_USER")),
              new ArrayList<>());// поебень с созданием наверное?

      return userRepository.save(user);
   }




   @Override
   public User saveCreate(UserCreatingDto creatingDto) {
      var user = new User(creatingDto.getFirstName(),
              creatingDto.getLastName(),
              creatingDto.getEmail(),
              passwordEncoder.encode(creatingDto
                      .getPassword()),
              Arrays.asList(new Role(creatingDto.getRole())),
              new ArrayList<>());

      return userRepository.save(user);
   }

   @Override
   public UserDetails loadUserByUsername(String username)
           throws UsernameNotFoundException {

      var user = userRepository.findUserByEmail(username);
      if(user !=null)          System.out.println("Пользователь найден!");
      if (user == null) {
         System.out.println("UserRepository: username or pass is not found");
         throw new UsernameNotFoundException
                 ("Invalid username or password.hgdftydf");
      }
      try {
         return new org.springframework.security
                 .core.userdetails.User(user.getEmail(),
                 user.getPassword(),
                 mapRolesToAuthorities(user.getRoles()));
      }
      catch (Exception e) {
         System.out.println(e.getMessage());
         System.out.println("не удалось вернуть элемент");
      }
      return null;
   }

   private Collection<? extends GrantedAuthority>
   mapRolesToAuthorities(Collection<Role> roles) {

      return roles.stream()
              .map(role -> new SimpleGrantedAuthority
                      (role.getName()))
              .collect(Collectors.toList());
   }

   @Override
   public List<User> getAll() {

      return userRepository.findAll();
   }

   public String getNameByEmail(String email) {
      return userRepository.findUserByEmail(email).getFirstName();
   }
   @Override
   @Transactional
   public User findUserByEmail(String email) {
      return userRepository.findUserByEmail(email);
   }

   @Override
   public User findDoctorById(Long id) {
      return userRepository.findDoctorById(id);
   }

   @Override
   public List<User> findDoctorBySpec(String spec) {
      List<User> doctors = new ArrayList<>();
      List<Long> longUser= specService.findBySpeciality(spec);
      for(User user : userRepository.findAll()) {
         if(longUser.contains(user.getId())) {
            doctors.add(user);
         }
      }
      return doctors;
   }

   public List<User> getDoctor(){
      List<User> userList = userRepository.findAll();
      List<User> doctorList = new ArrayList<>();
      for(User user : userList){
         if(user.getRoles().contains("ROLE_DOCTOR")){
            doctorList.add(user);
         }
      }
      return doctorList;
   }
   public List<User> searchUsers(String keyword) {
      return userRepository.searchByKeyword(keyword);
   }
}
