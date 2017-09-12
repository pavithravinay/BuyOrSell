package bean;

public class Address {

	private int id;
	private int userId;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;

	public Address() {

	}

	public Address(int id, int userId, String address, String city, String state, String country, String zipcode) {
		super();
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String toString() {
		return "Address [id=" + id + ", address=" + address + ", city=" + city + ", country=" + country + ", zipcode="
				+ zipcode + "]";
	}

}
