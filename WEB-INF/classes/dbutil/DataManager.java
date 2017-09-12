package dbutil;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.fileupload.FileItem;
import bean.Address;
import bean.Advertisement;
import bean.Category;
import bean.Image;
import bean.Message;
import bean.User;
import bean.UserCredential;

public class DataManager {

	private MySQL mySql = null;
	
	public DataManager() {
		mySql = MySQL.getInstance();
	}
	
	/* Category CRUD Start */
	public void addCategory(Category category) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, category.getId());
		valueMap.put(2, category.getName());
		valueMap.put(3, category.getParentCategory());
		mySql.executeStatement(SQLQueries.ADD_CATEGORY, valueMap);
	}
	
	public CachedRowSet getCategories() {
		return mySql.executeSelect(SQLQueries.GET_ALL_CATEGORIES, null);
	}
	
	public CachedRowSet getCategories(int parentCategoryId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, parentCategoryId);
		return mySql.executeSelect(SQLQueries.GET_CHILD_CATEGORIES, valueMap);
	}
	
	public void updateCategory(Category category) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, category.getName());
		valueMap.put(2, category.getParentCategory());
		valueMap.put(3, category.getId());
		mySql.executeStatement(SQLQueries.UPDATE_CATEGORY, valueMap);
	}
	
	public void deleteCategory(int categoryId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, categoryId);
		mySql.executeStatement(SQLQueries.DELETE_CATEGORY, valueMap);
	}
	/* Category CRUD End */
	
	
	/* Advertisement CRUD Start */
	public void addAdvertisement(Advertisement ad) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, ad.getId());
		valueMap.put(2, ad.getTitle());
		valueMap.put(3, ad.getDescription());
		valueMap.put(4, ad.getItemCondition());
		valueMap.put(5, ad.getCategory().getId());
		valueMap.put(6, ad.getImageName());
		valueMap.put(7, ad.isNegotiable());
		valueMap.put(8, ad.getPrice());
		valueMap.put(9, ad.getAddress().getId());
		valueMap.put(10, ad.getContactNumber());
		valueMap.put(11, ad.isDisplayContactNumber());
		valueMap.put(12, ad.getPostedBy().getId());
		valueMap.put(13, ad.getPostedDate());
		valueMap.put(14, ad.getUpdatedDate());
		valueMap.put(15, ad.isActive());
		mySql.executeStatement(SQLQueries.CREATE_ADVERTISEMENT, valueMap);
	}
	
	public void updateAdvertisement(Advertisement ad) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, ad.getTitle());
		valueMap.put(2, ad.getDescription());
		valueMap.put(3, ad.getItemCondition());
		valueMap.put(4, ad.getCategory().getId());
		valueMap.put(5, ad.getImageName());
		valueMap.put(6, ad.isNegotiable());
		valueMap.put(7, ad.getPrice());
		valueMap.put(8, ad.getAddress().getId());
		valueMap.put(9, ad.getContactNumber());
		valueMap.put(10, ad.isDisplayContactNumber());
		valueMap.put(11, ad.getUpdatedDate());
		valueMap.put(12, ad.isActive());
		valueMap.put(13, ad.isSoldOut());
		valueMap.put(14, ad.getId());

		mySql.executeStatement(SQLQueries.UPDATE_ADVERTISEMENT, valueMap);
	}
	
	public void updateSellingStatus(int advertismentId, boolean isSoldOut) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, isSoldOut);
		valueMap.put(2, advertismentId);
		mySql.executeStatement(SQLQueries.UPDATE_ADVERTISEMENT_SOLD, valueMap);
	}
	
	public void updateFeaturedStatus(int advertismentId, boolean isFeatured) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, isFeatured);
		valueMap.put(2, advertismentId);
		mySql.executeStatement(SQLQueries.UPDATE_ADVERTISEMENT_FEATURED, valueMap);
	}
	
	public void updateViewCount(int advertismentId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, advertismentId);
		mySql.executeStatement(SQLQueries.UPDATE_ADVERTISEMENT_VIEWCOUNT, valueMap);
	}
	
	public CachedRowSet getAdvertisements() {
		return mySql.executeSelect(SQLQueries.GET_ALL_ADS, null);
	}
	
	public CachedRowSet getAdvertisement(int advertisementId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, advertisementId);
		return mySql.executeSelect(SQLQueries.GET_ADVERTISEMENT_BY_ID, valueMap);
	}
	
	public CachedRowSet getAdvertisementsByUser(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_ADVERTISEMENT_BY_USER, valueMap);
	}
	
	public void deleteAdvertisement(int advertisementId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, advertisementId);
		mySql.executeStatement(SQLQueries.DELETE_ADVERTISEMENT, valueMap);
	}
	
	private String getCategoriesValue(int categoryId) {
		StringBuilder categories = new StringBuilder();
		if (CacheHandler.getArrangedCategories().containsKey(categoryId)) {
			for(Category c: CacheHandler.getArrangedCategories().get(categoryId)) {
				categories.append(c.getId()).append(",");
			}
			categories.deleteCharAt(categories.length() - 1);
		} else {
			categories.append(categoryId);
		}
		return categories.toString();
	}
	
	public CachedRowSet getAdvertisementsFromCategory(int categoryId) {
		/*HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, categoryId);
		valueMap.put(2, categoryId);*/
		String value = getCategoriesValue(categoryId);
		return mySql.executeSelect(String.format(SQLQueries.GET_ADVERTISEMENT_BY_CATEGORY, value), null);
	}
	/* Advertisement CRUD End */
	
	
	/*Image*/
	
	public int addImage(Image image, FileItem fileItem) {
		
		return mySql.executeFileStatementWithId(SQLQueries.CREATE_IMAGE, image, fileItem);
	}
	
	public Blob getAdvertisementsImage(int imageId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, imageId);
		return mySql.getImageBlob(SQLQueries.GET_IMAGE_BY_ID, valueMap);
	}
	
	public Blob getAdvertisementsImageByAdId(int imageId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, imageId);
		return mySql.getImageBlob(SQLQueries.GET_IMAGE_BY_ADID, valueMap);
	}
	/*ImageEND*/
	
	/* User CRUD Start */
	public void addUser(User user) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, user.getId());
		valueMap.put(2, user.getFirstName());
		valueMap.put(3, user.getLastName());
		valueMap.put(4, user.getGender());
		valueMap.put(5, user.getDateOfBirth());
		valueMap.put(6, user.getContactNumber());
		valueMap.put(7, user.getEmail());
		valueMap.put(8, user.getRole());
		//valueMap.put(9, user.getAddress().getId());
		mySql.executeStatement(SQLQueries.CREATE_USER, valueMap);
	}
	
	public void updateUser(User user) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, user.getFirstName());
		valueMap.put(2, user.getLastName());
		valueMap.put(3, user.getGender());
		valueMap.put(4, user.getDateOfBirth());
		valueMap.put(5, user.getContactNumber());
		valueMap.put(6, user.getEmail());
		valueMap.put(7, user.getRole());
		valueMap.put(8, user.getAddress().getId());
		valueMap.put(9, user.getId());
		mySql.executeStatement(SQLQueries.UPDATE_USER, valueMap);
	}
	
	public CachedRowSet getUser(String email) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, email);
		return mySql.executeSelect(SQLQueries.GET_USER_BY_EMAIL, valueMap);
	}
	
	public CachedRowSet getUser(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_USER_BY_ID, valueMap);
	}
	
	public CachedRowSet validateToken(String token) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, token);
		return mySql.executeSelect(SQLQueries.GET_USER_BY_VERIFICATION_TOKEN, valueMap);
	}
	/* User CRUD End */
	
	
	/* User Address CRUD Start */
	public CachedRowSet getDefaultAddress(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_DEFAULT_ADDRESS, valueMap);
	}
	
	public CachedRowSet getAddresses(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_ALL_ADDRESSES, valueMap);
	}
	
	public CachedRowSet getAddress(int addressId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, addressId);
		return mySql.executeSelect(SQLQueries.GET_ADDRESS, valueMap);
	}
	
	public void addAddress(Address address) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, address.getId());
		valueMap.put(2, address.getUserId());
		valueMap.put(3, address.getAddress());
		valueMap.put(4, address.getCity());
		valueMap.put(5, address.getState());
		valueMap.put(6, address.getCountry());
		valueMap.put(7, address.getZipcode());
		mySql.executeStatement(SQLQueries.ADD_ADDRESS, valueMap);
	}
	
	public void updateAddress(Address address) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, address.getAddress());
		valueMap.put(2, address.getCity());
		valueMap.put(3, address.getState());
		valueMap.put(4, address.getCountry());
		valueMap.put(5, address.getZipcode());
		valueMap.put(6, address.getId());
		mySql.executeStatement(SQLQueries.ADD_ADDRESS, valueMap);
	}
	
	public CachedRowSet getAddressAlreadyBindCount(int addressId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, addressId);
		return mySql.executeSelect(SQLQueries.GET_ADS_COUNT_BINDTO_ADDRESS, valueMap);
	}
	/* User Address CRUD End */
	
	
	/* User Credential CRUD Start */
	public void addUserCredential(User user) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		UserCredential credential = user.getCredential();
		valueMap.put(1, user.getId());
		valueMap.put(2, credential.getPassword());
		valueMap.put(3, credential.getVerificationCode());
		valueMap.put(4, credential.getAccountCreatedOn());
		valueMap.put(5, credential.getTokenValidTill());
		mySql.executeStatement(SQLQueries.CREATE_USER_CREDENTIAL, valueMap);
	}
	
	public void updatePassword(User user) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, user.getCredential().getPassword());
		valueMap.put(2, user.getId());
		mySql.executeStatement(SQLQueries.UPDATE_PASSWORD, valueMap);
	}
	
	public void updatePasswordResetToken(User user) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, user.getCredential().getVerificationCode());
		valueMap.put(2, user.getCredential().getTokenValidTill());
		valueMap.put(3, user.getId());
		mySql.executeStatement(SQLQueries.UPDATE_PASSWORD_RESET_TOKEN, valueMap);
	}
	/* User Credential CRUD Ends */
	
	
	/* Message CRUD Start */
	public void addMessage(Message message) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, message.getId());
		valueMap.put(2, message.getFromUserId());
		valueMap.put(3, message.getToUserId());
		valueMap.put(4, message.getMessage());
		valueMap.put(5, message.isRead());
		valueMap.put(6, message.getDateTime());
		mySql.executeStatement(SQLQueries.NEW_MESSAGE, valueMap);
	}
	
	public CachedRowSet getReceivedMessages(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_RECEIVED_MESSAGES, valueMap);
	}
	
	public CachedRowSet getSentMessages(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_SENT_MESSAGES, valueMap);
	}
	
	public void updateMessageReadStatus(int id, boolean status) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, status);
		valueMap.put(2, id);
		mySql.executeStatement(SQLQueries.UPDATE_MESSAGE_READ_STATUS, valueMap);
	}
	
	public void deleteMessageAsRead(int id) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, id);
		mySql.executeStatement(SQLQueries.DELETE_MESSAGE, valueMap);
	}
	
	public CachedRowSet getUnreadMessagesCount(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_UNREAD_MESSAGE_COUNT, valueMap);
	}
	/* Message CRUD End */
	
	/* <Pavithra> */
	public CachedRowSet getLimitedAdvertisements(int pageLimit, int pageNumber) {
		HashMap<Integer, Object> valueMap = new HashMap<>();

		valueMap.put(1, ((pageLimit * (pageNumber - 1))));
		valueMap.put(2, (Integer) pageLimit);
		return mySql.executeSelect(SQLQueries.GET_LIMITED_ADS, valueMap);
	}

	public CachedRowSet getTotalAdvertisementCount() {
		return mySql.executeSelect(SQLQueries.GET_TOTAL_AD_COUNT, null);
	}

	public CachedRowSet getLimitedAdvertisementsByCategory(int pageLimit, int pageNumber, int categoryId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, ((pageLimit * (pageNumber - 1))));
		valueMap.put(2, (Integer) pageLimit);
		String value = getCategoriesValue(categoryId);
		return mySql.executeSelect(String.format(SQLQueries.GET_LIMITED_ADS_BY_CATEGORY, value), valueMap);
	}

	public CachedRowSet getTotalAdvertisementCountByCategory(int categoryId) {
		/*HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, (Integer) categoryId);*/
		String value = getCategoriesValue(categoryId);
		return mySql.executeSelect(String.format(SQLQueries.GET_TOTAL_AD_COUNT_BY_CATEGORY, value), null);
	}
	public CachedRowSet getMostViewedAds(){
		return mySql.executeSelect(SQLQueries.GET_TOP_VIEWED_ADS,null);
	}
	/* </Pavithra> */
	
	/*</Damini>*/
	public CachedRowSet getSearchList(String searchId){
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1,searchId + "%");
		return mySql.executeSelect(SQLQueries.GET_PRODUCT_NAME, valueMap);
	}
	/*</Damini>*/
	
	/* Rohan */
	public void removeAddress(int addressId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, addressId);
		mySql.executeStatement(SQLQueries.DELETE_ADDRESS, valueMap);
	}

	public void deleteAdvertisementImage(int adid) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, adid);
		mySql.executeStatement(SQLQueries.DELETE_IMAGE, valueMap);
	}
	/* Rohan */
	
	/* Wishlist CRUD Start */
	public void addToWishList(int userId, int advertisementId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		valueMap.put(2, advertisementId);
		mySql.executeStatement(SQLQueries.ADD_TO_WISHLIST, valueMap);
	}
	
	public void removeFromWishList(int userId, int advertisementId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		valueMap.put(2, advertisementId);
		mySql.executeStatement(SQLQueries.REMOVE_FROM_WISHLIST, valueMap);
	}
	
	public CachedRowSet getWishList(int userId) {
		HashMap<Integer, Object> valueMap = new HashMap<>();
		valueMap.put(1, userId);
		return mySql.executeSelect(SQLQueries.GET_WISHLIST, valueMap);
	}
	/* Wishlist CRUD End */
	
	/* Tester Method */
	public static void main(String[] args) {
		DataManager crud = new DataManager();
		
		/*User user = new User(77452, "Gautam", "Mishra", "Male", new Date(1991, 1, 1), "3126238087", "gmishra2@hawk.iit.edu", "User", new Address(45454, 77452, "S Michigan Ave", "Chicago", "IL", "US", "60616"), "gmishra");
		crud.addUser(user);
		crud.addUserCredential(user);
		crud.addAddress(user.getAddress());*/
		crud.updateViewCount(7);
	}
}
