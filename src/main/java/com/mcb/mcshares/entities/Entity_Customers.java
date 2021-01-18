package com.mcb.mcshares.entities;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Entity_Customers {

	private Long id;
	private String customerId;
	private String customerType;
	private Date dateOfBirth;
	private String registrationNo;
	private Date dateIncorp;
	private Double balance;
	private String contactName;
	private Long numShares;
	private Double sharesPrice;

	private Entity_SharesDetails sharesDetails;
	private Entity_CustomerContactDetails contactDetails;
	private Entity_MailingAddresses mailingAddress;

	

	public Entity_SharesDetails getSharesDetails() {
		return sharesDetails;
	}

	public void setSharesDetails(Entity_SharesDetails sharesDetails) {
		this.sharesDetails = sharesDetails;
	}
	
	public Double getSharesPrice() {
		return sharesPrice;
	}

	public void setSharesPrice(Double sharesPrice) {
		this.sharesPrice = sharesPrice;
	}
	
	public Long getNumShares() {
		return numShares;
	}

	public void setNumShares(Long numShares) {
		this.numShares = numShares;
	}

	public Entity_CustomerContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(Entity_CustomerContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Entity_MailingAddresses getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(Entity_MailingAddresses mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Date getDateIncorp() {
		return dateIncorp;
	}

	public void setDateIncorp(Date dateIncorp) {
		this.dateIncorp = dateIncorp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Entity_Customers that = (Entity_Customers) o;
		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		
		if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
			return false;
		
		if (customerType != null ? !customerType.equals(that.customerType) : that.customerType != null)
			return false;
		
		if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null)
			return false;
		if (registrationNo != null ? !registrationNo.equals(that.registrationNo) : that.registrationNo != null)
			return false;
		if (dateIncorp != null ? !dateIncorp.equals(that.dateIncorp)
				: that.dateIncorp != null)
			return false;
		
		if (sharesDetails != null ? !sharesDetails.equals(that.sharesDetails) : that.sharesDetails != null)
			return false;
		if (contactDetails != null ? !contactDetails.equals(that.contactDetails) : that.contactDetails != null)
			return false;
		if (mailingAddress != null ? !mailingAddress.equals(that.mailingAddress): that.mailingAddress != null)
			return false;
		if (balance != null ? !balance.equals(that.balance): that.balance != null)
			return false;
		if (contactName != null ? !contactName.equals(that.contactName): that.contactName != null)
			return false;
		if (numShares != null ? !numShares.equals(that.numShares) : that.numShares != null)
			return false;
		if (sharesPrice != null ? !sharesPrice.equals(that.sharesPrice) : that.sharesPrice != null)
			return false;
		return true;
	}
	
	
	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		result = 31 * result + (customerType != null ? customerType.hashCode() : 0);
		result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
		result = 31 * result + (registrationNo != null ? registrationNo.hashCode() : 0);
		result = 31 * result + (dateIncorp != null ? dateIncorp.hashCode() : 0);
		result = 31 * result + (sharesDetails != null ? sharesDetails.hashCode() : 0);
		result = 31 * result + (contactDetails != null ? contactDetails.hashCode() : 0);
		result = 31 * result + (mailingAddress != null ? mailingAddress.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
		result = 31 * result + (numShares != null ? numShares.hashCode() : 0);
		result = 31 * result + (sharesPrice != null ? sharesPrice.hashCode() : 0);
		
		return result;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

}
