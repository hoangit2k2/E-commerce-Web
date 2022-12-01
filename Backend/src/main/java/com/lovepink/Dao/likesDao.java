package com.lovepink.Dao;

import com.lovepink.entity.Content;
import com.lovepink.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface likesDao  extends JpaRepository<Likes,Integer> {
    List<Likes> findByUsernameid(String usernameid);
//    List<Likes> findByUsername(String usernameid);

}
