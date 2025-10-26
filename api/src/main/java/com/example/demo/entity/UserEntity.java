package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.validator.UserValidateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserEntity {

    private Integer id;

    @NotBlank(groups = {
        UserValidateGroup.Create.class
    })
    @Size(min = 2, max = 16, groups = {
        UserValidateGroup.Create.class
    })
    private String username;

    private String name;

    @NotBlank(groups = {
        UserValidateGroup.Create.class, 
        UserValidateGroup.Update.class
	})
	@Size(min = 6, max = 16, groups = {
		UserValidateGroup.Create.class, 
		UserValidateGroup.Update.class
	})
    //后端不返回密码
    // 密码字段只能写入（用于接收数据），但不能读取（不会在JSON序列化时返回）。
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private LocalDateTime deletedAt;
	
	private LocalDateTime registeredAt;

    private String avatar;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", username=" + username + ", name=" + name + ", password=" + password
                + ", deletedAt=" + deletedAt + ", registeredAt=" + registeredAt + ", avatar=" + avatar + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((deletedAt == null) ? 0 : deletedAt.hashCode());
        result = prime * result + ((registeredAt == null) ? 0 : registeredAt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (deletedAt == null) {
            if (other.deletedAt != null)
                return false;
        } else if (!deletedAt.equals(other.deletedAt))
            return false;
        if (registeredAt == null) {
            if (other.registeredAt != null)
                return false;
        } else if (!registeredAt.equals(other.registeredAt))
            return false;
        return true;
    }



}
