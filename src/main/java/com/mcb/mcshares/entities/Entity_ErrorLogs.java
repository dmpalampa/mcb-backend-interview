package com.mcb.mcshares.entities;

import javax.persistence.*;
import java.sql.Timestamp;

public class Entity_ErrorLogs {
    private Long errorId;
    private String errorMessage;
    private String errorCode;
    
    public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity_ErrorLogs that = (Entity_ErrorLogs) o;
        if (errorId != null ? !errorId.equals(that.errorId) : that.errorId != null) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = errorId != null ? errorId.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        return result;
    }
}
