package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import bean.Address;
import bean.User;
import dbutil.DataManager;
import util.CommonUtils;

public class UserService implements IUser {

	DataManager dataManager = null;
	
	public UserService() {
		dataManager = new DataManager();
	}
	
	@Override
	public User autheticate(String email, String password) {
		/*System.out.println(email);
		System.out.println(password);*/
		CachedRowSet row = dataManager.getUser(email);
		try {
			if (row != null) {
				if (row.next()) {
					User user = new User(row.getInt("id"), row.getString("firstName"), row.getString("lastName"),
							row.getString("gender"), row.getDate("dateOfBirth"), row.getString("contactNumber"),
							row.getString("email"), row.getString("role"), getUserAddress(row.getInt("id")), row.getString("password"));
					boolean isAuthentic = user.getCredential().getPassword().equals(password);
					if(isAuthentic)
						return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean register(User user) {
		dataManager.addUser(user);
		dataManager.addUserCredential(user);
		if(user.getAddress() != null)
			dataManager.addAddress(user.getAddress());
		return true;
	}

	@Override
	public boolean updateProfile(User newProfile) {
		dataManager.addAddress(newProfile.getAddress());
		dataManager.updateUser(newProfile);
		return true;
	}
	
	@Override
	public boolean updatePassword(User user) {
		dataManager.updatePassword(user);
		return true;
	}

	@Override
	public boolean forgotPassword(String email) {
		User user = getUser(email);
		if(user != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(new Date());
			c.add(Calendar.DATE, 2);
			Date validityToken = c.getTime();
			
			String verificationCode = UUID.randomUUID().toString();
			
			user.getCredential().setVerificationCode(verificationCode);
			user.getCredential().setTokenValidTill(validityToken);
			dataManager.updatePasswordResetToken(user);
			
			String subject = "Reset Password - BuyOrSell";
			String message = "<strong><p>Dear %s, </p>"
					+ "<p>Click on the link below to reset your password.</p>"
					+ "<p>http://localhost:8080/CSP595_Project/forgot-password?token=%s</p>"
					+ "<p>Thanks, <br/>BuyOrSell.com</p></strong>";
			message = String.format(message, user.getFirstName(), verificationCode);
			
			CommonUtils.sendEmail(user.getEmail(), subject, message);
			return true;
		}
		return false;
	}

	@Override
	public boolean resetPassword(String validationToken) {
		User user = validateToken(validationToken);
		
		if(user != null) {
			String randomPassword = UUID.randomUUID().toString().substring(0, 10);
			
		}
		
		
		//user.getCredential().setPassword(randomPassword);
		//updatePassword(user);
		//System.out.println("NEW PASSWORD => " + randomPassword);
		return false;
	}
	
	@Override
	public User validateToken(String validationToken) {
		User user = null;
		CachedRowSet row = dataManager.validateToken(validationToken);
		try {
			if (row != null) {
				if (row.next()) {
					user = new User(row.getInt("id"), row.getString("firstName"), row.getString("lastName"),
							row.getString("gender"), row.getDate("dateOfBirth"), row.getString("contactNumber"),
							row.getString("email"), row.getString("role"), getUserAddress(row.getInt("id")), row.getString("password"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUser(String email) {
		User user = null;
		CachedRowSet row = dataManager.getUser(email);
		try {
			if (row != null) {
				if (row.next()) {
					user = new User(row.getInt("id"), row.getString("firstName"), row.getString("lastName"),
							row.getString("gender"), row.getDate("dateOfBirth"), row.getString("contactNumber"),
							row.getString("email"), row.getString("role"), getUserAddress(row.getInt("id")), row.getString("password"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User getUser(int userId) {
		User user = null;
		CachedRowSet row = dataManager.getUser(userId);
		try {
			if (row != null) {
				if (row.next()) {
					user = new User(row.getInt("id"), row.getString("firstName"), row.getString("lastName"),
							row.getString("gender"), row.getDate("dateOfBirth"), row.getString("contactNumber"),
							row.getString("email"), row.getString("role"), getUserAddress(row.getInt("id")), row.getString("password"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Address getUserAddress(int userId) {
		Address address = null;
		CachedRowSet row = dataManager.getDefaultAddress(userId);
		try {
			if (row != null) {
				if (row.next()) {
					address = new Address(row.getInt("id"), row.getInt("userId"), row.getString("address"),
							row.getString("city"), row.getString("state"), row.getString("country"),
							row.getString("zipcode"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	@Override
	public List<Address> getUserAddresses(int userId) {
		List<Address> addresses = new ArrayList<>();
		CachedRowSet row = dataManager.getAddresses(userId);
		try {
			if (row != null) {
				while (row.next()) {
					Address address = new Address(row.getInt("id"), row.getInt("userId"), row.getString("address"),
							row.getString("city"), row.getString("state"), row.getString("country"),
							row.getString("zipcode"));
					addresses.add(address);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addresses;
	}
	
	public static void main(String[] args) {
		CommonUtils.sendEmail("gautam.mishra@hotmail.com", "Subject", "This is a test message.");
	}
	
	@Override
	public boolean addAddress(Address address) {
		dataManager.addAddress(address);
		return true;
	}
	
	@Override
	public boolean removeAddress(int addressId) {
		dataManager.removeAddress(addressId);
		return true;
	}
	
	@Override
	public boolean doesAddressAlreadyBind(int addressId) {
		int count = 0;
		
		CachedRowSet row = dataManager.getAddressAlreadyBindCount(addressId);
		try {
			if (row != null) {
				if (row.next()) {
					count = row.getInt("count");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count>0) {
			return true;
		}
		return false;
	}
}
