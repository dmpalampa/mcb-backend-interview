
package com.mcb.mcshares.entities;

import javax.persistence.*;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Entity_XmlFiles{
	private Long fId;
	private String fName;
	private byte[] fContent;
	private String fType;




	public Long getfId() {
		return fId;
	}

	public void setfId(Long fId) {
		this.fId = fId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public byte[] getfContent() {
		return fContent;
	}

	public void setfContent(byte[] fContent) {
		this.fContent = fContent;
	}

	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Entity_XmlFiles that = (Entity_XmlFiles) o;

		if (fId != null ? !fId.equals(that.fId) : that.fId != null)
			return false;
		if (fName != null ? !fName.equals(that.fName) : that.fName != null)
			return false;
		if (fContent != null ? !fContent.equals(that.fContent) : that.fContent != null)
			return false;
		if (fType != null ? !fType.equals(that.fType) : that.fType != null)
			return false;

	
		return true;
	}

	@Override
	public int hashCode() {
		int result = fId != null ? fId.hashCode() : 0;
		result = 31 * result + (fName != null ? fName.hashCode() : 0);
		result = 31 * result + (fContent != null ? fContent.hashCode() : 0);
		result = 31 * result + (fType != null ? fType.hashCode() : 0);
		return result;
	}

}
