package com.ps20697.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20697.Entity.Order;

public interface OrderDAO  extends JpaRepository<Order, Long>{

}
