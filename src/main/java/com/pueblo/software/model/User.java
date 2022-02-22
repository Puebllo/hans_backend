package com.pueblo.software.model;

 
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.enums.LanguageCodeEnum;
import com.pueblo.software.interfaces.EntityInterface;
 
@Entity
@Table(name="users",schema="public")
public class User implements EntityInterface{

	private static final long serialVersionUID = -5714573640909437022L;
	
    public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String REGISTER_DATE = "registerDate";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String LOGIN = "login";
    public static final String PERSON = "person";
    public static final String LANGUAGE_CODE_ENUM = "languageCodeEnum";
    
	public Long id;
    public Long version;
    public String login;
    public String password;
    public Boolean isUserEnabled;
    public Boolean isUserLocked;
    public Boolean isAccountNonExpired;
    public Boolean areCredentialsNonExpired;
    public String email;
    public LocalDateTime registerDate;
    public LanguageCodeEnum languageCodeEnum;
    public Person person;

 
 
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
    

 
    @Column(name="login", unique=true, nullable=false)
    public String getLogin() {
        return login;
    }
 
    public void setLogin(String login) {
        this.login = login;
    }
 
    @Column(name="password", nullable=false)
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Column(name="email", nullable=false)
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name = "register_date", nullable = false)
    public LocalDateTime getRegisterDate(){
        return registerDate;
    }

    public void setRegisterDate( LocalDateTime registerDate ){
        this.registerDate = registerDate;
    }


    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "language_code_enum", nullable = false)
    public LanguageCodeEnum getLanguageCodeEnum() {
        return languageCodeEnum;
    }

    public void setLanguageCodeEnum(LanguageCodeEnum languageCodeEnum) {
        this.languageCodeEnum = languageCodeEnum;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }
 

    @Override
    public String toString() {
        return login + " (" + person.getFirstName() + " " + person.getLastName()+ ")";
        
    }

    @Column(name="is_user_enabled", nullable = false)
	public Boolean getIsUserEnabled() {
		return isUserEnabled;
	}

	public void setIsUserEnabled(Boolean isUserEnabled) {
		this.isUserEnabled = isUserEnabled;
	}

    @Column(name="is_user_locked", nullable = false)
	public Boolean getIsUserLocked() {
		return isUserLocked;
	}

	public void setIsUserLocked(Boolean isUserLocked) {
		this.isUserLocked = isUserLocked;
	}

    @Column(name="is_account_non_expired", nullable = false)
	public Boolean getIsAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

    @Column(name="are_credentials_non_expired", nullable = false)
	public Boolean getAreCredentialsNonExpired() {
		return areCredentialsNonExpired;
	}

	public void setAreCredentialsNonExpired(Boolean areCredentialsNonExpired) {
		this.areCredentialsNonExpired = areCredentialsNonExpired;
	}
 
 
     
}