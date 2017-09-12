package dbutil;

import java.util.*;
import com.mongodb.*;

import bean.Review;
import util.Constants;

public class MongoDBUtilities {

	private MongoClient objMongoClient = null;
	private DB objDB = null;
	private DBCollection objDBCollection = null;
	
	public MongoDBUtilities() {
		try {
			objMongoClient = new MongoClient("localhost", 27017);
			objDB = objMongoClient.getDB(Constants.MongoDatabaseName);
			objDBCollection = objDB.getCollection(Constants.MongoCollectionName);
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		objMongoClient.close();
		super.finalize();
	}

	public boolean addAdvertisementReview(Review objReview) {
		BasicDBObject objBasicDBObject = new BasicDBObject("rating", objReview.getRating())
				.append("review", objReview.getReview()).append("reviewDate", objReview.getReviewDate())
				.append("userId", objReview.getUserId()).append("advertisementId", objReview.getAdvertisementId());

		return insertIntoMongoDb(objBasicDBObject);
	}
	
	public boolean addSellerReview(Review objReview) {
		BasicDBObject objBasicDBObject = new BasicDBObject("rating", objReview.getRating())
				.append("review", objReview.getReview()).append("reviewDate", objReview.getReviewDate())
				.append("userId", objReview.getUserId()).append("sellerId", objReview.getSellerId());

		return insertIntoMongoDb(objBasicDBObject);
	}
	
	private boolean insertIntoMongoDb(BasicDBObject objBasicDBObject) {
		try {
			WriteResult objWriteResult = objDBCollection.insert(objBasicDBObject);
			if (objWriteResult.wasAcknowledged()) {
				return true;
			}
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Review> getSellerReviews(int sellerId) {
		List<Review> reviews = new ArrayList<>();
		Review objReview = null;
		DBCursor objDBCursor = null;
		
		try {
			BasicDBObject objBasicDBObject = new BasicDBObject("sellerId", sellerId);
			DBObject sort = new BasicDBObject();
			sort.put("reviewDate", -1);
			objDBCursor = objDBCollection.find(objBasicDBObject).sort(sort);
			
			while (objDBCursor.hasNext()) {
				DBObject objDBObject = objDBCursor.next();
				objReview = new Review();
				objReview.setRating(Float.parseFloat(objDBObject.get("rating").toString()));
				objReview.setReview((String) objDBObject.get("review"));
				objReview.setReviewDate((Date) objDBObject.get("reviewDate"));
				objReview.setUserId(Integer.parseInt(objDBObject.get("userId").toString()));
				objReview.setSellerId(Integer.parseInt(objDBObject.get("sellerId").toString()));

				reviews.add(objReview);
			}
		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			objDBCursor.close();
		}
		return reviews;
	}

	public List<Review> getAdvertisementReviews(int advertisementId) {
		List<Review> reviews = new ArrayList<>();
		Review objReview = null;
		DBCursor objDBCursor = null;
		
		try {
			BasicDBObject objBasicDBObject = new BasicDBObject("advertisementId", advertisementId);
			DBObject sort = new BasicDBObject();
			sort.put("reviewDate", -1);
			objDBCursor = objDBCollection.find(objBasicDBObject).sort(sort);
			
			while (objDBCursor.hasNext()) {
				DBObject objDBObject = objDBCursor.next();
				objReview = new Review();
				objReview.setRating(Float.parseFloat(objDBObject.get("rating").toString()));
				objReview.setReview((String) objDBObject.get("review"));
				objReview.setReviewDate((Date) objDBObject.get("reviewDate"));
				objReview.setUserId(Integer.parseInt(objDBObject.get("userId").toString()));
				objReview.setAdvertisementId(Integer.parseInt(objDBObject.get("advertisementId").toString()));

				reviews.add(objReview);
			}
		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			objDBCursor.close();
		}
		return reviews;
	}
	
	public int getAverageSellerRating(int sellerId) {
		int iAverageRating = 0;
		try {			
			DBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("_id", "$sellerId");
			groupFields.put("averageRating", new BasicDBObject("$avg", "$rating"));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject match = new BasicDBObject("$match", new BasicDBObject("sellerId", sellerId));

			AggregationOutput aggregate = objDBCollection.aggregate(match, group);
			for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				float fAverageRating = Float.parseFloat(bobj.getString("averageRating"));
				iAverageRating = Math.round(fAverageRating);
			}

		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return iAverageRating;
	}
	
	public int getAverageAdvertismentRating(int advertisementId) {
		int iAverageRating = 0;
		try {			
			DBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("_id", "$advertisementId");
			groupFields.put("averageRating", new BasicDBObject("$avg", "$rating"));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject match = new BasicDBObject("$match", new BasicDBObject("advertisementId", advertisementId));

			AggregationOutput aggregate = objDBCollection.aggregate(match, group);
			for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				float fAverageRating = Float.parseFloat(bobj.getString("averageRating"));
				iAverageRating = Math.round(fAverageRating);
			}

		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return iAverageRating;
	}

	/* <Pavithra> */
	// Method returns top 6 rated ads based on review rating from MongoDB
	public LinkedHashMap<Integer, Float> getTopRatedAds() {

		/*
		 * Hashmap<Integer, Float> : Integer= Advertisement Id, Float= Average
		 * review rating for the ad;
		 */
		LinkedHashMap<Integer, Float> topRatedAds = new LinkedHashMap<Integer, Float>();

		try {
			DBObject query = new BasicDBObject();
			DBObject match = new BasicDBObject("$match", query);
			DBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("_id", "$advertisementId");
			groupFields.put("average", new BasicDBObject("$avg", "$rating"));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject sort = new BasicDBObject();
			sort.put("average", -1);
			DBObject orderby = new BasicDBObject("$sort", sort);

			DBObject limit = new BasicDBObject("$limit", 6); // Limit to only 6
																// ads;

			AggregationOutput output = objDBCollection.aggregate(match, group, orderby, limit);

			for (final DBObject result : output.results()) {
				topRatedAds.put(Integer.parseInt(result.get("_id").toString()),
						Float.parseFloat(result.get("average").toString()));
			}

		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return topRatedAds;
	}

	/*
	 * Method returns top rated sellers based on seller rating from MongoDB For
	 * a top rated seller the seller rating should be >= 4
	 */
	public LinkedHashMap<Integer, Float> getTopRatedSellers() {

		LinkedHashMap<Integer, Float> topRatedSellers = new LinkedHashMap<Integer, Float>();

		try {
			DBObject query = new BasicDBObject();
			query.put("rating", new BasicDBObject("$gte", 4));
			DBObject match = new BasicDBObject("$match", query);
			DBObject groupFields = new BasicDBObject("_id", 0);
			groupFields.put("_id", "$sellerId");
			groupFields.put("average", new BasicDBObject("$avg", "$rating"));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject sort = new BasicDBObject();
			sort.put("average", -1);
			DBObject orderby = new BasicDBObject("$sort", sort);

			AggregationOutput output = objDBCollection.aggregate(match, group, orderby);

			for (final DBObject result : output.results()) {
				topRatedSellers.put(Integer.parseInt(result.get("_id").toString()),
						Float.parseFloat(result.get("average").toString()));
			}

		} catch (MongoException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return topRatedSellers;
	}
	
	public static void main(String[] args) {
		System.out.println((new MongoDBUtilities()).getTopRatedAds());
	}

	/* </Pavithra> */
}