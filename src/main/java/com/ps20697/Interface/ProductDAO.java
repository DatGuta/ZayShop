package com.ps20697.Interface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ps20697.Entity.Product;
import com.ps20697.Entity.Report;

public interface ProductDAO extends JpaRepository<Product, Integer>{
	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double minPrice, double maxPrice);

//	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1")
//	Page<Product> findByKeywords(String keywords, Pageable pageable);
	Page<Product> findAllByNameLike(String keywords, Pageable pageable);
	
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 AND o.type LIKE ?2")
	Page<Product> findAllByNameLikeAndTypeLike(String keywords, String type, Pageable pageable);
	
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 AND o.gender = ?2")
	Page<Product> findByNameLikeAndGenderLike(String keywords, Boolean gender, Pageable pageable);
	
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 AND o.gender = ?2 AND o.type LIKE ?3")
	Page<Product> findByNameLikeAndGenderLikeAndTypeLike(String keywords, Boolean gender,String type, Pageable pageable);
	
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 AND o.gender = ?2 AND o.category.name LIKE ?3")
	Page<Product> findByNameLikeAndGenderLikeAndCategoryLike(String keywords, Boolean gender,String category, Pageable pageable);
	
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1  AND o.category.name LIKE ?2")
	Page<Product> findAllByNameLikeAndCategoryLike(String keywords, String category, Pageable pageable);
	
	@Query("SELECT new Report(o.category, sum(o.price), count(o)) " + " FROM Product o " + " GROUP BY o.category"
			+ " ORDER BY sum(o.price) DESC")
	List<Report> getInventoryByCategory();
}
