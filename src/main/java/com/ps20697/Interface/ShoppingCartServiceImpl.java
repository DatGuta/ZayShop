package com.ps20697.Interface;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.ps20697.Entity.Cart;

import com.ps20697.Entity.Product;
import com.ps20697.Entity.ProductDetails;
import com.ps20697.Service.ShoppingCartService;


@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	Map<Integer, Cart> map = new HashMap<>();
	@Autowired
	ProductDAO dao;
	@Autowired
	ProductDetailDAO PDdao;	
	@Autowired
	ImageDAO daoImage;
	private Double totalPrice =  (double) 0;
	@Override
	public Cart add(Integer id, String size, int quantity, String color) {
		// TODO Auto-generated method stub
		Cart item = map.get(id);
		Product data = dao.findById(id).get(); 
		List<ProductDetails> detail = data.getProductDetails();
		Boolean note =true;
		for (int i = 0; i < detail.size()-1; i++) {
			if (detail.get(i).getColor().equals(color)&& detail.get(i).getSize().equals(size)) {
				note =true;
			}
			
		}
		if (item == null) {
			item = new Cart();
			item.setId(id);
			item.setName(data.getName());
			item.setColor(color);
			item.setNote(note);
			item.setSize(size);
			item.setQuantity(quantity);
			item.setPrice(data.getPrice()*item.getQuantity());
			item.setImage(data.getImages().get(0).getImagecode());
			map.put(id, item);
		}else {
			item.setQuantity(item.getQuantity()+quantity);
		}
		return item;
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub
		map.remove(id);
	}

	@Override
	public Cart update(Integer id, int qty) {
		// TODO Auto-generated method stub
		Cart item = map.get(id);
		item.setQuantity(qty);
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		map.clear();
	}

	@Override
	public Collection<Cart> getItems() {
		// TODO Auto-generated method stub
		return map.values();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return map.values().stream().mapToInt(Item -> Item.getQuantity()).sum();
	}

	@Override
	public Double getAmount() {
		Set<Integer> set = map.keySet();
		for (Integer key : set) {
            totalPrice += map.get(key).getPrice();
        }
		return totalPrice;
	}
	@Override
	public Cart getItem(Integer id) {
		Cart item  = map.get(id);
		return item;		
	}

}
