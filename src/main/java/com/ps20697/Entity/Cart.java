package com.ps20697.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	int id;
	String name;
	String size;
	int quantity;
	boolean note;
	String image;
	String color;
	Double price;
}
