package com.bohdans.userbase.repository;

import com.bohdans.userbase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
