package org.ss.jdbc.dao.domain;

import java.util.HashSet;
import java.util.Set;

public class Address {

	private Long id;
	private String details;
	private Set<Contact> contacts;

	public Address() {
		this.contacts = new HashSet<Contact>();
	}

	public Address(String details) {
		this.contacts = new HashSet<Contact>();
		this.details = details;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addContact(Contact c) {
		contacts.add(c);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}

}
