package bean;
import java.util.Date;

public class Advertisement {
	private int id;
	private String title;
	private String description;
	private String itemCondition;
	private Category category;
	private String imageName;
	private boolean isNegotiable;
	private float price;
	private Address address;
	private String contactNumber;
	private boolean displayContactNumber;
	private User postedBy;
	private Date postedDate;
	private Date updatedDate;
	private boolean isActive;
	private boolean isFeatured;
	private boolean isSoldOut;
	
	// Pavithra -Added new instance variable
	private int viewCount;

	public Advertisement() {

	}
	
	/* Constructor for Display All Advertisements (Minimal Data) */
	public Advertisement(int id, String title, String description, String itemCondition, Category category,
			String imageName, boolean isNegotiable, float price) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.itemCondition = itemCondition;
		this.category = category;
		this.imageName = imageName;
		this.isNegotiable = isNegotiable;
		this.price = price;
	}

	/* Constructor for Updating Advertisement */
	public Advertisement(int id, String title, String description, String itemCondition, Category category,
			String imageName, boolean isNegotiable, float price, Address address, String contactNumber,
			boolean displayContactNumber, boolean isActive) {
		this(id, title, description, itemCondition, category, imageName, isNegotiable, price);
		this.address = address;
		this.contactNumber = contactNumber;
		this.displayContactNumber = displayContactNumber;
		this.updatedDate = new Date();
		this.isActive = isActive;
	}
	
	/* Constructor for Posting Advertisement */
	public Advertisement(int id, String title, String description, String itemCondition, Category category,
			String imageName, boolean isNegotiable, float price, Address address, String contactNumber,
			boolean displayContactNumber, User postedBy, boolean isActive) {		
		
		this(id, title, description, itemCondition, category, imageName, isNegotiable, price, address, contactNumber, displayContactNumber, isActive);
		this.postedBy = postedBy;
		this.postedDate = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isNegotiable() {
		return isNegotiable;
	}

	public void setNegotiable(boolean isNegotiable) {
		this.isNegotiable = isNegotiable;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean isDisplayContactNumber() {
		return displayContactNumber;
	}

	public void setDisplayContactNumber(boolean displayContactNumber) {
		this.displayContactNumber = displayContactNumber;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isFeatured() {
		return isFeatured;
	}

	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public boolean isSoldOut() {
		return isSoldOut;
	}

	public void setSoldOut(boolean isSoldOut) {
		this.isSoldOut = isSoldOut;
	}

	public String toString() {
		return "Advertisement [id=" + id + ", title=" + title + ", postedBy=" + postedBy + ", postedDate="
				+ postedDate + "]";
	}
	
	/* <Pavithra> */
	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/* </Pavithra> */
}
