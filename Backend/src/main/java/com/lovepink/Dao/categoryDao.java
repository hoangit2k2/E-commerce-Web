package com.lovepink.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lovepink.entity.Category;

public interface categoryDao extends JpaRepository<Category, Integer> {

}
