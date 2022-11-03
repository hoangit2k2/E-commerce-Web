package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.Content;

public interface contentDao extends JpaRepository<Content, Integer> {

}
