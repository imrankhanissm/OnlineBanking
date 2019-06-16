package com.example.demo.controllers;

import java.sql.Date;
import java.util.Optional;
import java.util.Random;

import com.example.demo.configurations.EmailConfiguration;
import com.example.demo.models.AddressModel;
import com.example.demo.models.ResetPasswordModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.ResetPasswordRepository;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
@RequestMapping("/")
class HomeController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ResetPasswordRepository resetPasswordRepository;

	@Autowired
	EmailConfiguration emailConfiguration;

	// @Autowired
	// JavaMailSender javaMailSender;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("register")
	public String register(Authentication auth){
		if(auth != null)	return "/home";
		return "register";
	}

	@PostMapping("register")
	@ResponseBody
	public String register(Authentication auth, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("gender") boolean gender, @RequestParam("dateOfBirth") Date dateOfBirth, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("country") String country, @RequestParam("pincode") String pincode, @RequestParam("street") String street, @RequestParam("doorNo") String doorNo){
		if(userRepository.findByUsername(username) != null){
			return "username already exists";
		}
		if(userRepository.findByemail(email) != null){
			return "email alreday registered";
		}
		if(userRepository.findByphone(phone) != null){
			return "phone already registered";
		}
		AddressModel addressModel = new AddressModel(country, pincode, street, doorNo);
		UserModel userModel = new UserModel(username, passwordEncoder.encode(password), name, gender, email, phone, dateOfBirth, (long) 0,
				addressModel);
		if(userRepository.save(userModel) != null)	return "registered";
		return "not registered";
	}

	@GetMapping({"login"})
	public String login(Authentication auth){
		if(auth != null)	return "/home";
		return "login";
	}

	@GetMapping("forgotPassword")
	public String forgotPassword(){
		return "forgotPassword";
	}

	@PostMapping("forgotPassword")
	public String forgotPassword(@RequestParam("username") String username){
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel == null){
			return "forgotPassword";
		}

		String alphaNumbericSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		+ "0123456789"
		+ "abcdefghijklmnopqrstuvxyz";
		String token = "";
		int tokenLength = 64;
		Random random = new Random();
		for(int i=0;i<tokenLength;++i)	token += alphaNumbericSymbols.charAt(random.nextInt(alphaNumbericSymbols.length()));
		
		resetPasswordRepository.deleteByUserModel(userModel);
		if(resetPasswordRepository.save(new ResetPasswordModel(userModel, token)) == null){
			return "forgotPassword";
		}
		
		try {
			JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
			mailSenderImpl.setHost(emailConfiguration.getHost());
			mailSenderImpl.setPort(emailConfiguration.getPort());
			mailSenderImpl.setUsername(emailConfiguration.getUsername());
			mailSenderImpl.setPassword(emailConfiguration.getPassword());

			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("campusinter2@gmail.com");
			simpleMailMessage.setTo("imrankhanissm@gmail.com");
			simpleMailMessage.setSubject(username + "password reset");
			// simpleMailMessage.setText(String.format("<a href='localhost:8080/resetPassword/%s/%s'>Click here to reset password</a>", username, token));
			simpleMailMessage.setText("localhost:8080/"+ "resetPassword/" + username + "/" + token);
			
			mailSenderImpl.send(simpleMailMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "forgotPassword";
		}
		
		// mailSenderImpl.send(simpleMailMessage);

		return "forgotPasswordEmailSent";
	}

	@GetMapping("resetPassword/{username}/{token}")
	public String resetPassword(@PathVariable("username") String username, @PathVariable("token") String token, Model model){
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel != null){
			ResetPasswordModel resetPasswordModel = resetPasswordRepository.findByUserModelAndToken(userModel, token);
			if(resetPasswordModel != null){
				// resetPasswordRepository.delete(resetPasswordModel);
				model.addAttribute("username", username);
				model.addAttribute("token", token);
				return "createNewPassword";
			}
		}
		return "/hello/invalidUsernameOrToken";
	}

	@PostMapping("updatePassword")
	public String updatePassword(@RequestParam("username") String username, @RequestParam("token") String token, @RequestParam("password") String password){
		UserModel userModel = userRepository.findByUsername(username);
		ResetPasswordModel resetPasswordModel = resetPasswordRepository.findByUserModelAndToken(userModel, token);
		if(userModel != null && resetPasswordModel != null){
			userModel.setPassword(passwordEncoder.encode(password));
			userRepository.save(userModel);
			resetPasswordRepository.delete(resetPasswordModel);
			return "login";
		}
		return "/hello/invalidUsernameOrToken";
	}

	@RequestMapping({"", "home"})
	public String home(Model model, Authentication auth) {
		if(auth != null)	model.addAttribute("name", auth.getName());
		else	return "/login";
		return "home.html";
	}

	@GetMapping("profile")
	public String profile(Authentication auth, Model model){
		String username = auth.getName();
		UserModel userModel = userRepository.findByUsername(username);
		Optional<AddressModel> addressModel = addressRepository.findById(userModel.getAddressModel().getId());
		if(!addressModel.isPresent())	return "/login";
		model.addAttribute("address", addressModel.get());
		model.addAttribute("user", userModel);
		return "profile";
	}

	@GetMapping("editProfile")
	public String editProfile(Authentication auth, Model model){
		String username = auth.getName();
		UserModel userModel = userRepository.findByUsername(username);
		Optional<AddressModel> addressModel = addressRepository.findById(userModel.getAddressModel().getId());
		if(!addressModel.isPresent()) return "/login";
		model.addAttribute("user", userModel);
		model.addAttribute("address", addressModel.get());
		return "editProfile";
	}

	@PostMapping("editProfile")
	public String editProfile(Authentication auth, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("gender") boolean gender, @RequestParam("dateOfBirth") Date dateOfBirth, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("country") String country, @RequestParam("pincode") String pincode, @RequestParam("street") String street, @RequestParam("doorNo") String doorNo, Model model){
		String username = auth.getName();
		if(userRepository.findAllByEmail(email).size() > 1){
			return "/hello/email already registered";
		}
		if(userRepository.findAllByPhone(phone).size() > 1){
			return "hello";
		}
		UserModel userModel = userRepository.findByUsername(username);
		AddressModel addressModel = userModel.getAddressModel();
		if(password.length() > 0){
			userModel.setPassword(passwordEncoder.encode(password));
		}
		userModel.setName(name);
		userModel.setGender(gender);
		userModel.setDateOfBirth(dateOfBirth);
		userModel.setEmail(email);
		userModel.setPhone(phone);
		addressModel.setCountry(country);
		addressModel.setPincode(pincode);
		addressModel.setStreet(street);
		addressModel.setDoorNo(doorNo);
		userRepository.save(userModel);
		addressRepository.save(addressModel);
		model.addAttribute("address", addressModel);
		model.addAttribute("user", userModel);
		return "profile";
	}

	@RequestMapping("contactUs")
	public String contactUs(Authentication auth, Model model){
		if(auth != null)	model.addAttribute("name", auth.getName());
		return "contactUs";
	}

	// @RequestMapping("logoutSuccess")
	// public String logoutSuccess(){
	// 	return "logoutSuccess";
	// }

	@RequestMapping("hello/{error}")
	public String hello(@PathVariable("error") String error, Model model){
		model.addAttribute("error2", error);
		return "error";
	}
}