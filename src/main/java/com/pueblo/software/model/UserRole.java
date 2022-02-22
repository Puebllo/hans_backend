package com.pueblo.software.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.enums.UserRoleEnum;
import com.pueblo.software.interfaces.EntityInterface;
 
@Entity
@Table(name="user_role",schema="public")
public class UserRole implements EntityInterface{
 
	private static final long serialVersionUID = -6523580242696261787L;
	
    public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String ROLE = "role";

	public Long id;
    public Long version;
    public UserRoleEnum role;
 
     
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
    
    @Version
    public Long getVersion(){
        return version;
    }

    public void setVersion( Long version ){
        this.version = version;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    public UserRoleEnum getType() {
        return role;
    }
 
    public void setType(UserRoleEnum role) {
        this.role = role;
    }
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserRole))
            return false;
        UserRole other = (UserRole) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "UserProfile [id=" + id + ", type=" + role + "]";
    }
 
 
 
 
}