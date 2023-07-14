package com.ps20697.Service;

import java.util.Collection;

import com.ps20697.Entity.Cart;


public interface ShoppingCartService {
	Cart add(Integer id, String size, int quantity, String color);
	
	void remove(Integer id);
	
	Cart update(Integer id, int qty);
	
	void clear();
	
	Collection<Cart> getItems();
	
	int getCount();
	
	Double getAmount();
	
	Cart  getItem(Integer id);
	
}
