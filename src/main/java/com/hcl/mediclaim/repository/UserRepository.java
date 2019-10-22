package com.hcl.mediclaim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.User;

/**
 * User Repository interface
 * 
 * @author Laxman
 * @date 21-OCT-2019
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserId(Integer userId);

	Optional<User> findByEmailAndPassword(String email, String password);
}
