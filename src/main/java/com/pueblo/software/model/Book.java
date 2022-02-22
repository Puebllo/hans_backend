package com.pueblo.software.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="book",schema="public")
public class Book implements EntityInterface{
 

	private static final long serialVersionUID = -2522199662175984504L;

	public static final String NAME = "name";

	private Long id;
    public Long version;
	private String name;

    
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

	@Column(name="name", nullable= false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	

 
}