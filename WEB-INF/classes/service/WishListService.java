/* <Pavithra> */
package service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import bean.Advertisement;
import bean.Category;
import dbutil.CacheHandler;
import dbutil.DataManager;

public class WishListService implements IWishList {

	private DataManager dataManager = null;

	public WishListService() {
		dataManager = new DataManager();
	}

	@Override
	public void addToWishList(int userId, int advertisementId) {
		dataManager.addToWishList(userId, advertisementId);
	}

	@Override
	public void removeFromWishList(int userId, int advertisementId) {
		dataManager.removeFromWishList(userId, advertisementId);
	}

	@Override
//	public CachedRowSet getWishlist(int userId) {
//		CachedRowSet row = dataManager.getWishlist(userId);
//		return row;
//	}
	
	public List<Advertisement> getWishList(int userId) {
		CachedRowSet result = dataManager.getWishList(userId);
		List<Advertisement> wishList = new ArrayList<>();
		AdvertisementService service = new AdvertisementService();
		
		try {
			while (result.next()) {
				int id = result.getInt("AdvertisementId");
				Advertisement advertisement = service.getAdvertisement(id);
				wishList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishList;
		
	}

}

/* </Pavithra> */
