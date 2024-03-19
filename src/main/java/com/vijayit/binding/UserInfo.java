package com.vijayit.binding;

import lombok.Data;

@Data
public class UserInfo {

	private Long id;
	private String name;

    public UserInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
