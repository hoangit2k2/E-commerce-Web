package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.User;

public interface userDao extends JpaRepository<User, String> {

}
