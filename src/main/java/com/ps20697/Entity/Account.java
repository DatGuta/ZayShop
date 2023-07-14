package com.ps20697.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
	@Id
	String Username;
	String Password;
	String Fullname;
	String Email;
	String Photo;
	boolean Activated;
	boolean Admin;
	@OneToMany(mappedBy = "account")
	List<Order> orders;
}
