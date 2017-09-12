package service;

import java.util.HashMap;
import java.util.List;
import java.sql.Blob;

import org.apache.commons.fileupload.FileItem;
import bean.Advertisement;
import bean.Category;
import bean.Image;

public interface IAdvertisement {

	public boolean postAdvertisement(Advertisement ad);

	public boolean updateAdvertisement(Advertisement ad);

	public boolean deleteAdvertisement(int advertisementId);
	
	public List<Advertisement> getAdvertisements();
	
	public List<Advertisement> getAdvertisements(int categoryId);
	
	public Advertisement getAdvertisement(int advertisementId);
	
	/* <Pavithra> */	
	public List<Advertisement> getLimitedAdvertisements(int pageLimit, int pageNumber);
	
	public int getTotalAdvertisementCount();
	
	public List<Advertisement> getLimitedAdvertisementsByCategory(int pageLimit, int pageNumber, int categoryId);
	
	public int getTotalAdvertisementCountByCategory(int categoryId);
	
	public List<Advertisement> getAdvertisementBySeller(int sellerId);
	
	public List<Advertisement> getMostViewedAds();
	/* </Pavithra> */
	
	/* Nishu */	
	/*public Address getAdvertisementAddress(int userId);*/
	/* Nishu */
	
	/* Damini */
	public HashMap<Integer, String> getSuggestions(String prefix);
	/* Damini */
	
	/* Rohan */
	Image addAdvertisementImage(Image image);

	Image addAdvertisementImage(Image image, FileItem fileItem);

	Blob getAdvertisementImage(int id);

	Blob getAdvertisementImageByAdId(int adId);

	boolean deleteAdvertisementImage(int id);
	
	List<Category> getSubCategories(int parentCategoryId);
	/* Roahn */
}
