package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Artwork;
import com.example.demo.entity.User;
import com.example.demo.repository.ArtworkRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ArtworkController {

	@Autowired
	private ArtworkRepository artworkRepository;

	// Show upload page
	@GetMapping("/upload")
	public String showUploadPage() {
		return "upload"; // Renders upload.html
	}

	// Handle upload form submission
	@PostMapping("/upload")
	public String uploadArtwork(@RequestParam("title") String title, @RequestParam("type") String type,
			@RequestParam("size") String size, @RequestParam("price") double price,
			@RequestParam("thumbnailImage") MultipartFile thumbnailImage,
			@RequestParam("enlargedImage") MultipartFile enlargedImage, HttpSession session, Model model)
			throws IOException {
		// Save image files to server folder
		String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;;
		new File(uploadDir).mkdir(); // Create the folder if it doesn't exist
		// Save thumbnail image
		String thumbFilePath = uploadDir + thumbnailImage.getOriginalFilename();
		thumbnailImage.transferTo(new File(thumbFilePath));

		// Save enlarged image
		String largeFilePath = uploadDir + enlargedImage.getOriginalFilename();
		enlargedImage.transferTo(new File(largeFilePath));

		// Retrieve logged-in user (artist)
		User artist = (User) session.getAttribute("user");

		// Save artwork details to database
		Artwork artwork = new Artwork();
		artwork.setTitle(title);
		artwork.setType(type);
		artwork.setSize(size);
		artwork.setPrice(price);
		artwork.setThumbnailImage(thumbFilePath);
		artwork.setEnlargedImage(largeFilePath);
		artwork.setArtist(artist);

		artworkRepository.save(artwork);

		model.addAttribute("message", "Artwork uploaded successfully!");
		return "redirect:/home";
	}
	// Show all artworks from all users
    @GetMapping("/showArtworks")
    public String showAllArtworks(Model model) {
        List<Artwork> artworks = artworkRepository.findAll();  // Fetch all artworks from database
        model.addAttribute("artworks", artworks);
        return "showArtworks";  // Return to showArtworks.html page
    }
	
}
