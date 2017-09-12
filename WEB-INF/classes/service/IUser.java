package service;

import java.util.List;

import bean.Address;
import bean.User;

public interface IUser {

	public User autheticate(String email, String password);
	
	public User getUser(int userId);
	
	public User getUser(String email);
	
	public User validateToken(String token);

	public boolean register(User user);

	public boolean updateProfile(User newProfile);
	
	public boolean updatePassword(User user);

	public boolean forgotPassword(String email);

	public boolean resetPassword(String validationToken);
	
	public Address getUserAddress(int userId);
	
	public boolean addAddress(Address address);
	
	public List<Address> getUserAddresses(int userId);
	
	boolean removeAddress(int addressId);

	boolean doesAddressAlreadyBind(int addressId);
}
