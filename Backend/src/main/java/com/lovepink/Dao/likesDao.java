package com.lovepink.Dao;

import com.lovepink.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface likesDao  extends JpaRepository<Likes,String> {

}
