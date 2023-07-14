package com.ps20697.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20697.Entity.Category;

public interface CategoryDAO extends JpaRepository<Category, String>{

}
