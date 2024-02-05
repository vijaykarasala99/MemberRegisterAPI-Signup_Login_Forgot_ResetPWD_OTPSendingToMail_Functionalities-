package com.vijayit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijayit.entity.RegisterEntity;

public interface RegisterRepo extends JpaRepository<RegisterEntity, Long> {

	public RegisterEntity findByEmail(String email);
	
	public RegisterEntity findByEmailAndPwd(String email, String pwd);

}
