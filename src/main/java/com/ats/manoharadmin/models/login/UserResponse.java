package com.ats.manoharadmin.models.login;

import com.ats.manoharadmin.models.ErrorMessage;
import com.ats.manoharadmin.models.MUser;

public class UserResponse {
	MUser user;
	ErrorMessage errorMessage;
	public MUser getUser() {
		return user;
	}
	public void setMnUser(MUser user) {
		this.user = user;
	}
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "UserResponse [user=" + user + ", errorMessage=" + errorMessage + "]";
	}
	
	
	

}
