package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.Users;

public interface userDao extends JpaRepository<Users, String> {

}
