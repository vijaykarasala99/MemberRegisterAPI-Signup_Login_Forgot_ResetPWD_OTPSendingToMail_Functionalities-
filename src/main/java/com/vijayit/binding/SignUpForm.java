package com.vijayit.binding;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class SignUpForm {

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
