package com.knf.dev.demo.repository;

import com.knf.dev.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   void deleteById(Long id);
   User findUserByEmail(String email);
   User findDoctorById(Long id);
   @Query("SELECT u FROM User u WHERE u.firstName LIKE %:keyword% OR u.email LIKE %:keyword% OR u.lastName LIKE %:keyword%")
   List<User> searchByKeyword(@Param("keyword") String keyword);

}