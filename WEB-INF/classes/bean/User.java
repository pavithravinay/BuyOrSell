package bean;
import java.util.Date;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dateOfBirth;
	private String contactNumber;
	private String email;
	private String role;
	private Address address;
	private UserCredential credential;

	public User() {

	}

	public User(int id, String firstName, String lastName, String gender, Date dateOfBirth, String contactNumber,
			String email, String role, Address address, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.contactNumber = contactNumber;
		this.email = email;
		this.role = role;
		this.address = address;
		this.credential = new UserCredential(password, "-", new Date());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public UserCredential getCredential() {
		return credential;
	}

	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ",  email=" + email + "]";
	}
}
