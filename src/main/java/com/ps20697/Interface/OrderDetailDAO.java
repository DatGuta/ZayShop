package com.ps20697.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps20697.Entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{

}
