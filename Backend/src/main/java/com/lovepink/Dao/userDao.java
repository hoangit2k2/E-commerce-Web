package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.Users;

import java.util.List;

public interface userDao extends JpaRepository<Users, String> {
//    List<Users> findById(String username);

}
