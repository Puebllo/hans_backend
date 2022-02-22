package com.pueblo.software.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="persistent_login",schema="public")
public class PersistentLogin implements EntityInterface{

	private static final long serialVersionUID = -2203273541261818559L;

	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String LOGIN = "login";
	public static final String TOKEN = "token";
	public static final String LAST_USED = "lastUsed";

	public String series;
	public Long version;
	private String login;
	private String token;
	public LocalDate lastUsed;


	@Id
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
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

	@Column(name="token", unique=true, nullable=false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name="last_used", nullable=false)
	public LocalDate getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(LocalDate lastUsed) {
		this.lastUsed = lastUsed;
	}

    @Override
    public void setId(Long id) {
        setSeries(id.toString());
    }

    @Override
    public Long getId() {
        return Long.parseLong(getSeries());
    }


}