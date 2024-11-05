package com.shopme.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	@Test
	void passwordEncoderTes() {
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String rawPassword="Ansar123";
		String encode = bCryptPasswordEncoder.encode(rawPassword);
		System.out.println(encode);
		boolean matches = bCryptPasswordEncoder.matches(rawPassword, encode);
		
		assertThat(matches).isTrue();
		
	}

}
