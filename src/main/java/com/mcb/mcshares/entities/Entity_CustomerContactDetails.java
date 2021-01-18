
package com.mcb.mcshares.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Entity_CustomerContactDetails{
	private Long id;
	private String contactName;
	private String contactNumber;
	private Long customerId;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Entity_CustomerContactDetails that = (Entity_CustomerContactDetails) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null)
			return false;
		if (contactNumber != null ? !contactNumber.equals(that.contactNumber) : that.contactNumber != null)
			return false;
		if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
			return false;
	
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
		result = 31 * result + (contactNumber != null ? contactNumber.hashCode() : 0);
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		return result;
	}

}
