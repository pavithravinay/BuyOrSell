package dbutil;

public class SQLQueries {
	
	private SQLQueries() {}
	
	/* Queries for Categories */
	public final static String ADD_CATEGORY = "INSERT INTO category(id, name, parentCategory) VALUES (?, ?, ?);";
	
	public final static String UPDATE_CATEGORY = "UPDATE category SET name = ?, parentCategory = ? WHERE id = ?;";
	
	public final static String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?;";
	
	public final static String GET_CHILD_CATEGORIES = "SELECT * FROM category WHERE parentCategory = ?;";
	
	public final static String GET_ALL_CATEGORIES = "SELECT * FROM category;";
	
	
	/* Queries for Advertisements */
	public final static String CREATE_ADVERTISEMENT = "INSERT INTO  advertisement (id, title, description, itemCondition, "
			+ "categoryId, imageName, isNegotiable, price, addressId, contactNumber, displayContactNumber, postedBy, postedDate, updatedDate, isActive) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public final static String UPDATE_ADVERTISEMENT = "UPDATE advertisement SET title = ?, description = ?, itemCondition = ?, categoryId = ?, "
			+ "imageName = ?, isNegotiable = ?, price = ?, addressId = ?, contactNumber = ?, displayContactNumber = ?, updatedDate = ?, isActive = ?, isSoldOut = ? "
			+ "WHERE id = ?;";
	
	public final static String UPDATE_ADVERTISEMENT_SOLD = "UPDATE advertisement SET isSoldOut = ? WHERE id = ?;";
	
	public final static String UPDATE_ADVERTISEMENT_FEATURED = "UPDATE advertisement SET isFeatured = ? WHERE id = ?;";
	
	public final static String UPDATE_ADVERTISEMENT_VIEWCOUNT = "UPDATE advertisement SET viewCount = viewCount + 1 WHERE id = ?;";
	
	public final static String GET_ADVERTISEMENT_BY_ID = "SELECT * FROM advertisement WHERE id = ?;";
	
	public final static String GET_ADVERTISEMENT_BY_USER = "SELECT * FROM advertisement WHERE postedBy = ?;";
	
	public final static String DELETE_ADVERTISEMENT = "DELETE FROM advertisement WHERE id = ?;";
	
	public final static String GET_ALL_ADS = "SELECT * FROM advertisement WHERE isActive = 1;";
	
	public final static String GET_ADVERTISEMENT_BY_CATEGORY = "SELECT * FROM advertisement WHERE categoryId IN (%s);";
	
	/* Queries for User */
	public final static String CREATE_USER = "INSERT INTO user (id, firstName, lastName, gender, dateOfBirth, contactNumber, email, role) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	public final static String UPDATE_USER = "UPDATE user SET firstName = ?, lastName = ?, gender = ?, dateOfBirth = ?, contactNumber = ?,  email = ?, "
			+ "role = ?, addressId = ? WHERE id = ?;";
	
	public final static String UPDATE_PASSWORD = "UPDATE usercredential SET password = ? WHERE userId = ?";
	
	public final static String GET_USER_BY_EMAIL = "SELECT * FROM usercredential, user WHERE user.email = ? AND user.id = usercredential.userId";
	
	public final static String GET_USER_BY_ID = "SELECT * FROM usercredential, user WHERE user.id = ? AND user.id = usercredential.userId";
	
	public final static String GET_USER_BY_VERIFICATION_TOKEN = "SELECT * FROM usercredential uc, user u WHERE uc.verificationToken = ? AND uc.userId = u.id";
	
	/* Queries for User Address */
	public final static String ADD_ADDRESS = "INSERT INTO address (id, userId, address, city, state, country, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?);";
	
	public final static String UPDATE_ADDRESS = "UPDATE address SET address = ?, city = ?, state = ?, country = ?, zipCode = ? WHERE id = ?;";
	
	public final static String GET_DEFAULT_ADDRESS = "SELECT * FROM address WHERE id = (SELECT addressId FROM user WHERE user.id = ?);";
	
	public final static String GET_ALL_ADDRESSES = "SELECT * FROM address WHERE userId = ?;";
	
	public final static String GET_ADDRESS = "SELECT * FROM address WHERE id = ?;";

	/* Queries for User Credential */
	public final static String CREATE_USER_CREDENTIAL = "INSERT INTO usercredential	(userId, password, verificationToken, accountCreatedOn, tokenValidTill) "
			+ "VALUES (?, ?, ?, ?, ?);";
	
	public final static String UPDATE_USER_PASSWORD = "UPDATE usercredential SET password = ? WHERE userId = ?;";
	
	public final static String UPDATE_PASSWORD_RESET_TOKEN = "UPDATE usercredential SET verificationToken = ?, tokenValidTill = ? WHERE userId = ?";

	/* Queries for Messages */
	public final static String NEW_MESSAGE = "INSERT INTO message (id, fromUserId, toUserId, message, isRead, dateTime) VALUES (?, ?, ?, ?, ?, ?);";
	
	public final static String GET_RECEIVED_MESSAGES = "SELECT * FROM message WHERE toUserId = ?;";
	
	public final static String GET_SENT_MESSAGES = "SELECT * FROM message WHERE fromUserId = ?;";

	public final static String UPDATE_MESSAGE_READ_STATUS = "UPDATE message SET isRead = ? WHERE id = ?;";

	public final static String DELETE_MESSAGE = "DELETE FROM message WHERE id = ?";
	
	public final static String GET_UNREAD_MESSAGE_COUNT = "SELECT COUNT(*) AS 'count' FROM message WHERE toUserId = ? and isRead = 0;";
	
	/* <Pavithra> */
	public final static String GET_LIMITED_ADS = "SELECT * FROM advertisement  ORDER BY id LIMIT ?,?;";
	
	public final static String GET_TOTAL_AD_COUNT = "SELECT COUNT(*) AS totalCount FROM advertisement";
	
	public final static String GET_LIMITED_ADS_BY_CATEGORY = "SELECT * FROM advertisement WHERE categoryId IN (%s) ORDER BY id LIMIT ?,?;";
	
	public final static String GET_TOTAL_AD_COUNT_BY_CATEGORY = "SELECT COUNT(*) AS totalCount FROM advertisement WHERE categoryId IN (%s)";
	
	public final static String GET_TOP_VIEWED_ADS = "SELECT * FROM advertisement ORDER BY viewCount desc";
	/* </Pavithra> */
	
	/*</damini>*/
	public final static String GET_PRODUCT_NAME = "SELECT id, title from advertisement WHERE title LIKE ?";
	/*</damini>*/
	
	/* Rohan */
	/* Queries for Image */
	public final static String CREATE_IMAGE = "INSERT INTO  Image ( name, adId, photo)  VALUES (?, ?, ?);";
	
	public final static String DELETE_IMAGE = "DELETE FROM Image WHERE adId = ?;";
	
	public final static String GET_IMAGE_BY_ID ="select photo from Image where id = ?;";
	
	public final static String GET_IMAGE_BY_ADID ="select photo from Image where adid = ?;";
	
	public final static String GET_ADS_COUNT_BINDTO_ADDRESS = "SELECT COUNT(*) AS 'count' FROM advertisement WHERE addressId = ? ;";
	
	
	/* ADDRESS*/
	public final static String DELETE_ADDRESS = "DELETE FROM address WHERE id = ?;";
	/* Rohan */
	
	/* Queries for Wishlist */
	public final static String ADD_TO_WISHLIST = "INSERT INTO wishlist (UserId, AdvertisementId) VALUES (?, ?)";
	
	public final static String REMOVE_FROM_WISHLIST = "DELETE FROM wishlist WHERE UserId = ? AND AdvertisementId = ?;";
	
	public final static String GET_WISHLIST = "SELECT * FROM wishlist WHERE UserId = ?";
}
