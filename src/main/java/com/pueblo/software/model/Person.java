package com.pueblo.software.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.pueblo.software.enums.PersonTitleEnum;
import com.pueblo.software.interfaces.EntityInterface;


@Entity
@Table(name = "person", schema = "public")
public class Person implements EntityInterface {

    public static final long serialVersionUID = 1;

    public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String PERSON_TITLE = "personTitle";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String REGISTER_DATE = "registerDate";
    public static final String PERSON_PHOTO = "personPhoto";

	public Long id;
	public Long version;
	public PersonTitleEnum personTitle;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public LocalDateTime registerDate;
    public byte[] personPhoto;

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

	@Column(name = "first_name", nullable = false)
	public String getFirstName(){
		return firstName;
	}

	public void setFirstName( String firstName ){
		this.firstName = firstName;
	}


	@Column(name = "last_name", nullable = false)
	public String getLastName(){
		return lastName;
	}

	public void setLastName( String lastName ){
		this.lastName = lastName;
	}

	@Column(name = "date_of_birth", nullable = false)
	public LocalDate getDateOfBirth(){
		return dateOfBirth;
	}

	public void setDateOfBirth( LocalDate dateOfBirth ){
		this.dateOfBirth = dateOfBirth;
	}


	@Column(name = "register_date", nullable = false)
	public LocalDateTime getRegisterDate(){
		return registerDate;
	}

	public void setRegisterDate( LocalDateTime registerDate ){
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

    @Column(name = "person_title", nullable = false)
    @Enumerated(EnumType.STRING)
    public PersonTitleEnum getDictPersonTitle() {
        return personTitle;
    }

    public void setDictPersonTitle(PersonTitleEnum dictPersonTitle) {
        this.personTitle = dictPersonTitle;
    }

    @Column(name = "person_photo")
    public byte[] getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(byte[] personPhoto) {
        this.personPhoto = personPhoto;
    }

}
