package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.Content;

import java.util.List;
import java.util.Optional;

public interface contentDao extends JpaRepository<Content, Integer> {
    List<Content> findByUsernameid(String usernameid);
//    List<Content> findByCategoryId(String categoryid);

    List<Content> findByCategoryid(Integer categoryid);
}
