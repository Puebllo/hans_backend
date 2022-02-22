package com.pueblo.software.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="user_role_user_indirect",schema="public")
public class UserRoleUserIndirect implements EntityInterface{
	

	private static final long serialVersionUID = -8634012118137008604L;
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String USER_ROLE = "userRole";
	public static final String USER = "user";

	public Long id;
	public Long version;
	public UserRole userRole;
	public User user;

	   
    @Id
    @Column(name = "id", unique = true, nullable = false)
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
    
    @Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			NodeGroupNodeIndirect nodeGroupNodeIndirect = (NodeGroupNodeIndirect) obj;
			if (nodeGroupNodeIndirect.getId()!=null) {
				if (this.getId().equals(nodeGroupNodeIndirect.getId())) {
					return true;
				}
			}else {
				return false;
			}
		}else {
			return false;
		}
		return super.equals(obj);
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "users", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_role", nullable = false)
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	@Override
	public String toString() {
		return this.getUserRole() + " - " + this.getUser();
	}

}
