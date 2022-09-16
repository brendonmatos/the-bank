package com.thebank.auth;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
@Transactional
public class AuthService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User login(FazerAuthDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user == null) {
			throw new Error("User not found");
		}

		if (!BCrypt.checkpw(userDTO.getSenha(), user.getSaltedSenha())) {
			throw new Error("Password is incorrect");
		}
		return user;
	}

	public User setPassword(String email, String senha) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new Error("User not found with e-mail: " + email);
		}

		user.setSaltedSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
		return userRepository.save(user);
	}

	public User create(CriarAuthDTO userDTO) {
		User existingUser = userRepository.findByEmail(userDTO.getEmail());
		if (existingUser != null) {
			throw new Error("User already exists");
		}

		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setCpf(userDTO.getCpf());
		user.setNivel(userDTO.getNivel());
		user.setSaltedSenha(BCrypt.hashpw(userDTO.getSenha(), BCrypt.gensalt()));
		return userRepository.save(user);
	}

	public boolean remove(String cpf) {
		userRepository.deleteByCpf(cpf);
		return true;
	}

}
