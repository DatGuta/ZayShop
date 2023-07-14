package com.ps20697.Interface;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ps20697.Entity.Product;
import com.ps20697.Entity.ProductDetails;


public interface ProductDetailDAO extends JpaRepository<ProductDetails, Long>{
	@Query("SELECT o FROM ProductDetails o WHERE o.product =?1 AND o.color LIKE ?2 AND o.size LIKE ?3 AND o.count > 0")
	ProductDetails findByIdAndColorAndSizeAndCount(Product product , String color, String size);
}
