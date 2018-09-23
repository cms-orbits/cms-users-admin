/**
 * 
 */
package com.joelgtsantos.cmsusers.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joel Santos
 *
 * cms-users-admin
 * 2018
 */
public class InfoUsersRequestDTO {
	private Set<Long> users = new HashSet<>();
	
	public Set<Long> getUsers() {
		return users;
	}
	public void setUsers(Set<Long> users) {
		this.users = users;
	}
}
