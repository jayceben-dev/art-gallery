package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.Userrepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private Userrepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Renders login.html
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,HttpSession session) {

        // Validate user credentials using Hibernate
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
        	session.setAttribute("user", user);
            return "redirect:/home"; // Successful login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to login with error message
        }
    }
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Renders register.html
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String houseNo,
            @RequestParam String buildingName,
            @RequestParam String street,
            @RequestParam String city,
            @RequestParam String district,
            @RequestParam String state,
            @RequestParam String zipCode,
            @RequestParam String gender,
            @RequestParam String contactNumber,
            Model model) {

        // Check if user already exists
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "User already exists with this username");
            return "register";
        }

        // Create a new user and save to database
        User newUser = new User();
        newUser.setName(name);
        newUser.setAge(age);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setHouseNo(houseNo);
        newUser.setBuildingName(buildingName);
        newUser.setStreet(street);
        newUser.setCity(city);
        newUser.setDistrict(district);
        newUser.setState(state);
        newUser.setZipCode(zipCode);
        newUser.setGender(gender);
        newUser.setContactNumber(contactNumber);
        
        userRepository.save(newUser);

        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
    	// Retrieve user from session
    	User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            return "redirect:/login"; // Redirect to login if no user found in session
        }
    	return "home";
    }
}

   
