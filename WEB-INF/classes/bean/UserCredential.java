package bean;

import java.util.Date;

public class UserCredential {
	private Date accountCreatedOn;
	private Date tokenValidTill;
	private String password;
	private String verificationCode;

	public UserCredential() {

	}

	public UserCredential(String password, String verificationCode, Date tokenValidTill) {
		super();
		this.accountCreatedOn = new Date();
		this.password = password;
		this.verificationCode = verificationCode;
		this.tokenValidTill = tokenValidTill;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getTokenValidTill() {
		return tokenValidTill;
	}

	public void setTokenValidTill(Date tokenValidTill) {
		this.tokenValidTill = tokenValidTill;
	}

	public Date getAccountCreatedOn() {
		return accountCreatedOn;
	}

	public String toString() {
		return "UserCredential [password=" + password + ", verificationCode=" + verificationCode
				+ "]";
	}
}
