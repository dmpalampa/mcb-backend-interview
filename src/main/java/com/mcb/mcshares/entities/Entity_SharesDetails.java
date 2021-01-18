
package com.mcb.mcshares.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Entity_SharesDetails{
	
	private Long shareId;
	private Long numShares;
	private Double sharesPrice;
	private Double balance;
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	private Long customerId;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getShareId() {
		return shareId;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}
	
	public Double getSharesPrice() {
		return sharesPrice;
	}

	public void setSharesPrice(Double sharesPrice) {
		this.sharesPrice = sharesPrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Entity_SharesDetails that = (Entity_SharesDetails) o;

		if (shareId != null ? !shareId.equals(that.shareId) : that.shareId != null)
			return false;
		if (numShares != null ? !numShares.equals(that.numShares) : that.numShares != null)
			return false;
		if (sharesPrice != null ? !sharesPrice.equals(that.sharesPrice) : that.sharesPrice != null)
			return false;
		if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
			return false;
		if (balance != null ? !balance.equals(that.balance): that.balance != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = shareId != null ? shareId.hashCode() : 0;
		result = 31 * result + (numShares != null ? numShares.hashCode() : 0);
		result = 31 * result + (sharesPrice != null ? sharesPrice.hashCode() : 0);
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		return result;
	}

	public Long getNumShares() {
		return numShares;
	}

	public void setNumShares(Long numShares) {
		this.numShares = numShares;
	}

}
