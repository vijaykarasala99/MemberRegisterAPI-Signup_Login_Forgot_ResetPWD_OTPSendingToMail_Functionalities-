package com.vijayit.binding;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {

	private Long id;
	private String name;
	@Lob
	private byte[] pic;
	
	
	// need to verify otp
	private Long mobileno;
	// need to verify otp
	private String email;
	private String pwd;
	private LocalDate dob;
	private LocalDateTime time;
}
