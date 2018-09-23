/**
 * 
 */
package com.joelgtsantos.cmsusers.entity;

/**
 * @author Joel Santos
 *
 * cms-users-admin
 * 2018
 */
public class UserDTO {
	Long id;
	String name;
	String username;
	
	public UserDTO (Long id, String name, String username) {
		this.id = id;
		this.name = name;
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
