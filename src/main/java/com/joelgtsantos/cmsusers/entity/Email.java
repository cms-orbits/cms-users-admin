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
public class Email {
	
	private String name;
	private String from;
	private String to;
	private String subject;
	private String body;

	
	public Email() {}
	
	public Email(String name, String from, String to, String subject, String body) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
