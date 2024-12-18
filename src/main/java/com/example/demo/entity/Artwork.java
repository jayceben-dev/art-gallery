package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "artwork")
public class Artwork {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title; // Title of the work
	
	@Column(nullable = false)
	private String type; // Type of work (Painting, Sculpture, etc.)
	
	@Column(nullable = false)
	private String size; // Size of the work
	
	@Column(nullable = false)
	private double price; // Price of the work

	@Column(nullable = false)
	private String thumbnailImage; // Path for thumbnail image
	@Column(nullable = false)
	private String enlargedImage; // Path for enlarged image

	// Many artworks belong to one artist (user)
	@ManyToOne
	@JoinColumn(name = "user_id") // Foreign key to User table
	private User artist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public String getEnlargedImage() {
		return enlargedImage;
	}

	public void setEnlargedImage(String enlargedImage) {
		this.enlargedImage = enlargedImage;
	}

	public User getArtist() {
		return artist;
	}

	public void setArtist(User artist) {
		this.artist = artist;
	}
}
