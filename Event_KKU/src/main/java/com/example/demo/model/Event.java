package com.example.demo.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 500)
	private String title;

	@Column(length = 5000)
	private String description;
	
	private String location;
	
	private LocalDateTime startdate;
	
	private LocalDateTime enddate;

	private String category;

	private String image;
	
	private Boolean isActive;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event(Integer id, String title, String description, String location, LocalDateTime startdate, LocalDateTime enddate,
			String category, String image, Boolean isActive) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.startdate = startdate;
		this.enddate = enddate;
		this.category = category;
		this.image = image;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}

	public LocalDateTime getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description=" + description + ", location=" + location
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", category=" + category + ", image=" + image
				+ ", isActive=" + isActive + "]";
	}
	
	// เมธอดสำหรับแสดง startdate ในรูปแบบที่ต้องการ
	public String getFormattedStartDate() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM hh:mm a", Locale.ENGLISH);
	    return startdate.format(formatter);
	}

	// เมธอดสำหรับแสดง enddate ในรูปแบบที่ต้องการ
	public String getFormattedEndDate() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM hh:mm a", Locale.ENGLISH);
	    return enddate.format(formatter);
	}

}
