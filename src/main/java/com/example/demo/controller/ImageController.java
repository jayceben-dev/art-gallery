package com.example.demo.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {
	 private final Path uploadsDirectory = Paths.get("uploads");

	    @GetMapping("/uploads/{filename}")
	    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
	        try {
	            Path filePath = uploadsDirectory.resolve(filename);
	            Resource resource = new UrlResource(filePath.toUri());
	            if (resource.exists() || resource.isReadable()) {
	                return ResponseEntity.ok().body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (MalformedURLException e) {
	            return ResponseEntity.badRequest().build();
	        }
	    }

}
