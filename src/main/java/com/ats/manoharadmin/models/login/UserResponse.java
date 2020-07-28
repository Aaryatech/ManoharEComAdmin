package com.ats.manoharadmin.models.login;

import com.ats.manoharadmin.models.ErrorMessage;
import com.ats.manoharadmin.models.MnUser;

public class UserResponse {
	MnUser user;
	ErrorMessage errorMessage;
	public MnUser getUser() {
		return user;
	}
	public void setMnUser(MnUser user) {
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
