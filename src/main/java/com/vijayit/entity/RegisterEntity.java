package com.vijayit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class RegisterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

//	@Lob
//	private byte[] pic;
    
	 @Lob
	 @Column(columnDefinition = "MEDIUMBLOB")
	 private String pic;
	    
	// need to verify otp
	private Long mobileno;
	// need to verify otp
	private String email;
	private String pwd;
	private LocalDate dob;
	private LocalDateTime time;

}