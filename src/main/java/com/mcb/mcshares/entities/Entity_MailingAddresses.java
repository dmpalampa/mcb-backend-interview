package com.mcb.mcshares.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", schema = "public", catalog = "postgres")
public class Entity_MailingAddresses {
	private Long addressId;
	private String addressLine1;
	private String addressLine2;
	private String townCity;
	private String country;

	private Long customerId;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getTownCity() {
		return townCity;
	}

	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Entity_MailingAddresses that = (Entity_MailingAddresses) o;

		if (addressId != null ? !addressId.equals(that.addressId) : that.addressId != null)
			return false;

		if (addressLine1 != null ? !addressLine1.equals(that.addressLine1) : that.addressLine1 != null)
			return false;
		if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null)
			return false;
		if (townCity != null ? !townCity.equals(that.townCity) : that.townCity != null)
			return false;
		if (country != null ? !country.equals(that.country) : that.country != null)
			return false;
		if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
			return false;
		return true;
	}
	

	@Override
	public int hashCode() {
		int result = addressId != null ? addressId.hashCode() : 0;
		result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
		result = 31 * result + (addressLine1 != null ? addressLine1.hashCode() : 0);
		result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
		result = 31 * result + (townCity != null ? townCity.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		return result;
	}
}
