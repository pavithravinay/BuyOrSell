package service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import org.apache.commons.fileupload.FileItem;
import bean.Address;
import bean.Advertisement;
import bean.Category;
import bean.Image;
import bean.User;
import dbutil.CacheHandler;
import dbutil.DataManager;

public class AdvertisementService implements IAdvertisement {

	private DataManager dataManager = null;
	
	public AdvertisementService() {
		dataManager = new DataManager();
	}
	
	/* Rohan */	
	@Override
	public boolean postAdvertisement(Advertisement ad) {
		dataManager.addAdvertisement(ad);
		return true;
	}

	@Override
	public Image addAdvertisementImage(Image image) {
		return null;
	}

	@Override
	public List<Category> getSubCategories(int parentCategoryId) {
		/*CachedRowSet row = dataManager.getCategories(parentCategoryId);
		List<Category> categories = new ArrayList<Category>();
		try {
			if (row != null) {
				while (row.next()) {
					Category cat = new Category(row.getInt("id"), row.getString("name"), row.getInt("parentCategory"));
					categories.add(cat);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;*/
		return CacheHandler.getArrangedCategories().get(parentCategoryId);
	}
	
	@Override
	public Image addAdvertisementImage(Image image, FileItem fileItem) {
		int insertedId = dataManager.addImage(image, fileItem);

		if (insertedId > 0) {
			image.setId(insertedId);
			return image;
		}
		return null;
	}

	@Override
	public Blob getAdvertisementImage(int id) {
		Blob imageblob = dataManager.getAdvertisementsImage(id);
		return imageblob;
	}

	@Override
	public boolean deleteAdvertisementImage(int id) {
		dataManager.deleteAdvertisementImage(id);
		return true;
	}

	@Override
	public Blob getAdvertisementImageByAdId(int adId) {
		Blob imageblob = dataManager.getAdvertisementsImageByAdId(adId);
		return imageblob;
	}

	@Override
	public boolean deleteAdvertisement(int advertisementId) {
		dataManager.deleteAdvertisement(advertisementId);
		return true;
	}

	@Override
	public boolean updateAdvertisement(Advertisement ad) {
		dataManager.updateAdvertisement(ad);
		return true;
	}
	/* Rohan */

	/* <Pavithra> */
	@Override
	public List<Advertisement> getAdvertisements() {
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getAdvertisements();
		
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);
				
				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}

	@Override
	public List<Advertisement> getLimitedAdvertisements(int pageLimit, int pageNumber) {
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getLimitedAdvertisements(pageLimit, pageNumber);
		
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);
				
				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}

	/* get total count of advertisements */
	public int getTotalAdvertisementCount() {
		CachedRowSet totalAdCount = dataManager.getTotalAdvertisementCount();
		int adCount = 0;
		try {
			while (totalAdCount.next()) {
				adCount = totalAdCount.getInt("totalCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return adCount;

	}

	@Override
	public List<Advertisement> getAdvertisements(int categoryId) {
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getAdvertisementsFromCategory(categoryId);
		
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);
				
				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}
	
	public List<Advertisement> getAdvertisementBySeller(int sellerId) {
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getAdvertisementsByUser(sellerId);

		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);

				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}

	@Override
	public List<Advertisement> getLimitedAdvertisementsByCategory(int pageLimit, int pageNumber, int categoryId) {
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getLimitedAdvertisementsByCategory(pageLimit, pageNumber, categoryId);
		
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);
				
				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}

	@Override
	public int getTotalAdvertisementCountByCategory(int categoryId) {
		CachedRowSet totalAdCount = dataManager.getTotalAdvertisementCountByCategory(categoryId);
		int adCount = 0;
		try {
			while (totalAdCount.next()) {
				adCount = totalAdCount.getInt("totalCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return adCount;
	}
	
	public List<Advertisement> getMostViewedAds(){
		List<Advertisement> advertisementList = new ArrayList<>();
		CachedRowSet result = dataManager.getMostViewedAds();
		
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				String description = result.getString("description");
				String itemCondition = result.getString("itemCondition");
				Category category = CacheHandler.getCategory(result.getInt("categoryId"));
				String imageName = result.getString("imageName");
				Boolean isNegotiable = result.getBoolean("isNegotiable");
				float price = result.getFloat("price");

				Advertisement advertisement = new Advertisement(id, title, description, itemCondition, category, imageName, isNegotiable, price);
				
				
				advertisement.setUpdatedDate(result.getDate("updatedDate"));
				advertisement.setPostedDate(result.getDate("postedDate"));
				advertisementList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisementList;
	}

	/* </Pavithra> */

	
	/* Nishu */
	@Override
	public Advertisement getAdvertisement(int advertisementId) {
		Advertisement advertisement = null;
		CachedRowSet row = dataManager.getAdvertisement(advertisementId);
		try {
			if (row != null) {
				if (row.next()) {
					Address address = null;
					User user = (new UserService()).getUser(row.getInt("postedBy"));
					CachedRowSet rs = dataManager.getAddress(row.getInt("addressId"));
					
					if(rs != null) {
						if(rs.next()) {
							address = new Address(rs.getInt("id"), rs.getInt("userId"), rs.getString("address"),
									rs.getString("city"), rs.getString("state"), rs.getString("country"),
									rs.getString("zipcode"));
						}
					}
					
					advertisement = new Advertisement(row.getInt("id"), row.getString("title"),
							row.getString("description"), row.getString("itemCondition"),
							CacheHandler.getCategory(row.getInt("categoryId")), row.getString("imageName"),
							row.getBoolean("isNegotiable"), row.getFloat("price"), address,
							row.getString("contactNumber"), row.getBoolean("displayContactNumber"), user,
							row.getBoolean("isActive"));
					
					advertisement.setUpdatedDate(row.getDate("updatedDate"));
					advertisement.setPostedDate(row.getDate("postedDate"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisement;
	}

	/*@Override
	public Address getAdvertisementAddress(int userId) {
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
	}*/
	/* NIshu */
	
	/* Damini */
	
	@Override
	public HashMap<Integer, String> getSuggestions(String prefix) {
		DataManager dataManager = new DataManager();
		HashMap<Integer, String> search = new HashMap<Integer, String>();
		CachedRowSet result = dataManager.getSearchList(prefix);
		if(result != null) {
			try {
				while(result.next()) {
					search.put(result.getInt("id"), result.getString("title"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return search;
	}
	
	
	
	public static void main(String[] args) {
		AdvertisementService s = new AdvertisementService();
		System.out.println(s.getSuggestions("a"));
	}
	
	/* Damini */
}
