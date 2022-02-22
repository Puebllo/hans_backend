package com.pueblo.software.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.interfaces.EntityInterface;


@Entity
@Table(name = "automation_area", schema = "public")
public class AutomationArea implements EntityInterface {


	private static final long serialVersionUID = 3817627265571691608L;
	
	public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String AREA_NAME = "areaName";
    public static final String AREA_DESCRIPTION = "areaDescription";
    public static final String INSERT_DATE_TIME = "insertDateTime";

	public Long id;
	public Long version;
    public String areaName;
    public String areaDescription;
    public LocalDateTime insertDateTime;
    
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() { 
		return this.id;
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
		return id.intValue();
	}

	@Column(name = "area_name", nullable = false)
	public String getAreaName(){
		return areaName;
	}

	public void setAreaName( String areaName ){
		this.areaName = areaName;
	}


	@Column(name = "area_description", nullable = false)
	public String getAreaDescription(){
		return areaDescription;
	}

	public void setAreaDescription( String areaDescription ){
		this.areaDescription = areaDescription;
	}

	@Column(name = "insert_date_time", nullable = false)
	public LocalDateTime getInsertDateTime(){
		return insertDateTime;
	}

	public void setInsertDateTime( LocalDateTime insertDateTime ){
		this.insertDateTime = insertDateTime;
	}


	@Override
	public String toString() {
		return areaName + " " + areaDescription;
	}


}
