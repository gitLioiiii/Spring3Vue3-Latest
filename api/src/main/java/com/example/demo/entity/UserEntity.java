package com.example.demo.entity;

import com.example.demo.validator.UserValidateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserEntity {

    private Integer id;

    private String username;

    @NotBlank(groups = {
        UserValidateGroup.Create.class, 
        UserValidateGroup.Update.class
	})
	@Size(min = 6, max = 16, groups = {
		UserValidateGroup.Create.class, 
		UserValidateGroup.Update.class
	})
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
