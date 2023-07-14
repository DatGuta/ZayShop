package com.ps20697.Interface;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ps20697.Entity.Account;



public interface AccountDAO extends JpaRepository<Account, String>{
	@Query("SELECT o FROM Account o WHERE o.Username LIKE ?1 AND o.Password LIKE ?2")
	Account findByUserLikeAndPassLike(String username, String password);
	
	@Query("SELECT o FROM Account o WHERE o.Username LIKE ?1 ")
	Account findByUserLike(String username);
}
