package bean;

import java.util.*;

public class Review {

	private float rating;
	private String review;
	private Date reviewDate;
	private int userId; // Buyer ID
	private int sellerId; // For Seller Reviews
	private int advertisementId; // For Product Reviews

	public Review() {

	}

	public Review(float rating, String review, Date reviewDate, int userId, int sellerId, int advertisementId) {
		this.rating = rating;
		this.review = review;
		this.reviewDate = reviewDate;
		this.userId = userId;
		this.sellerId = sellerId;
		this.advertisementId = advertisementId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(int advertisementId) {
		this.advertisementId = advertisementId;
	}
	
	public String toString(){
		return "Review [rating=" + rating + ", review=" + review + ", reviewDate=" + reviewDate
				+ ", userId=" + userId + ", sellerId=" + sellerId + ", advertisementId=" + advertisementId + "]"; 
	}
}