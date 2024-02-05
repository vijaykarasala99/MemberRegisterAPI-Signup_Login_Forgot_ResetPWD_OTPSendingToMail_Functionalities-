package com.vijayit.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="members")
public class RegisterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fname;
	private String lname;
	private String familyname;
	private LocalDate dob;
	private LocalDateTime time;
	private Integer age;
    private String gender;
    //need to verify otp
    private Long mobileno;
    //need to verify otp
    private String email;
    private String pwd;
    private String placeofbirth;
    private String address;
    private String profession;
    private String martialstatus;
    private String disability;
}