package com.sourabh.entity;

public class News {
	
	String newsId;
	String title;
	String reporter;
	String postingDate;
	String endDate;
	String images;
	String detail;
	String categoryId;
	String commentsId;
	String type;
	String status;
	Category category;
	String locality;
	String advertiseImage;
	public String getAdvertiseImage() {
		return advertiseImage;
	}
	public void setAdvertiseImage(String advertiseImage) {
		this.advertiseImage = advertiseImage;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
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
	String city;
	String state;
	String country;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(String commentsId) {
		this.commentsId = commentsId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toStringBuilder(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(getCategory().getCategory()+" ");
		stringBuilder.append(getCategory().getDetail()+" ");
		stringBuilder.append(getDetail()+ " ");
		stringBuilder.append(getCity()+" ");
		stringBuilder.append(getReporter()+" ");
		stringBuilder.append(getCountry()+" ");
		stringBuilder.append(getLocality()+" ");
		stringBuilder.append(getState()+ " ");
		stringBuilder.append(getTitle()+" ");
		stringBuilder.append(getType()+" ");
		return stringBuilder.toString().toLowerCase();
	}
	public String toShare(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(getTitle()+" "+"\n\n");
		stringBuilder.append(getReporter()+ " \t"+getPostingDate()+"\n");
		stringBuilder.append(getDetail()+" "+"\n");
		
		
		return stringBuilder.toString();
	}

}
