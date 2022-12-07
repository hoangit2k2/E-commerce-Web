package com.lovepink.Dao;

import com.lovepink.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface commentDao extends JpaRepository<Comment, Integer> {
    List<Comment> findByUsernameid(String usernameid);

    List<Comment> findByContentid(Integer contentid);
}
