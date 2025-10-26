package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.validator.PhysitionValidateGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PhysicianEntity {
    
    @NotNull(
        groups = {
            PhysitionValidateGroup.Update.class
        }
    )
    @Min(
        value = 1,
        groups = {
            PhysitionValidateGroup.Update.class
        }
    )
    private Integer id;

    @NotNull(
        groups = {
            PhysitionValidateGroup.Create.class,
            PhysitionValidateGroup.Update.class
        }
    )
    @NotBlank(
        groups = {
            PhysitionValidateGroup.Create.class,
            PhysitionValidateGroup.Update.class
        }
    )
    private String username;

    private String password;

    @NotBlank(
        groups = {
            PhysitionValidateGroup.Create.class,
            PhysitionValidateGroup.Update.class
        }
    )
    private String name;

    private Integer age;

    @NotNull(groups = {PhysitionValidateGroup.Update.class})
    private Character gender;

    @JsonIgnore
    private LocalDateTime deletedAt;

    private PositionEntity position;

    private List<PositionEntity> positions;

    @NotNull(
        groups = {
            PhysitionValidateGroup.Create.class, 
            PhysitionValidateGroup.Update.class
        }
    )
    @Min(
        value = 1, 
        groups = {
            PhysitionValidateGroup.Create.class, 
            PhysitionValidateGroup.Update.class
        }
    )
    private Integer officeId;

    private OfficeEntity office;

    private String phone;

    @NotNull(groups = {PhysitionValidateGroup.Create.class})
    private String serve;

    @JsonIgnore
    private LocalDateTime registeredAt;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public OfficeEntity getOffice() {
        return office;
    }

    public void setOffice(OfficeEntity office) {
        this.office = office;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }

    public List<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionEntity> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "PhysicianEntity [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
                + ", age=" + age + ", gender=" + gender + ", deletedAt=" + deletedAt + ", officeId=" + officeId
                + ", office=" + office + ", phone=" + phone + ", serve=" + serve + ", registeredAt=" + registeredAt
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((deletedAt == null) ? 0 : deletedAt.hashCode());
        result = prime * result + ((officeId == null) ? 0 : officeId.hashCode());
        result = prime * result + ((office == null) ? 0 : office.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((serve == null) ? 0 : serve.hashCode());
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
        PhysicianEntity other = (PhysicianEntity) obj;
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
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (age == null) {
            if (other.age != null)
                return false;
        } else if (!age.equals(other.age))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (deletedAt == null) {
            if (other.deletedAt != null)
                return false;
        } else if (!deletedAt.equals(other.deletedAt))
            return false;
        if (officeId == null) {
            if (other.officeId != null)
                return false;
        } else if (!officeId.equals(other.officeId))
            return false;
        if (office == null) {
            if (other.office != null)
                return false;
        } else if (!office.equals(other.office))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (serve == null) {
            if (other.serve != null)
                return false;
        } else if (!serve.equals(other.serve))
            return false;
        if (registeredAt == null) {
            if (other.registeredAt != null)
                return false;
        } else if (!registeredAt.equals(other.registeredAt))
            return false;
        return true;
    }

}