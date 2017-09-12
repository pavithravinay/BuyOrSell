/* <Pavithra> */
package service;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import bean.Advertisement;

public interface IWishList {
	
	public void addToWishList(int userId, int advertisementId);
	public void removeFromWishList(int userId, int advertisementId);
	public List<Advertisement> getWishList(int userId);
	
}

/* </Pavithra> */
