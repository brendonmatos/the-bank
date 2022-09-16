package com.thebank.auth;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public User createUser(@Valid @RequestBody CriarAuthDTO userDTO) {
		User user = authService.create(userDTO);
		return user;
	}

	@PostMapping("/login")
	public User login(@Valid @RequestBody FazerAuthDTO userDTO) {
		User user = authService.login(userDTO);
		if (user == null) {
			throw new Error("User not found");
		}
		return user;
	}

}
