package com.pueblo.software.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.pueblo.software.enums.ReservedTopicEnum;
import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="reserved_topic",schema="public", uniqueConstraints= { @UniqueConstraint(columnNames = {"reserved_topic_enum"})})
public class ReservedTopic implements EntityInterface{


	private static final long serialVersionUID = 2094848153166795912L;
	
	public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String RESERVED_TOPIC_ENUM = "reservedTopicEnum";
    public static final String VALUE = "value";


	public Long id;
    public Long version;
    public ReservedTopicEnum reservedTopicEnum;
    public String value;
    

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
    
    @Enumerated(EnumType.STRING)
	@Column(name="reserved_topic_enum", nullable=false)
	public ReservedTopicEnum getReservedTopicEnum() {
		return reservedTopicEnum;
	}

	public void setReservedTopicEnum(ReservedTopicEnum reservedTopicEnum) {
		this.reservedTopicEnum = reservedTopicEnum;
	}
	
	@Column(name="value", nullable=false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			ReservedTopic node = (ReservedTopic) obj;
			if (node.getId()!=null) {
				if (this.getId().equals(node.getId())) {
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
	
    @Override
    public String toString() {
    	return reservedTopicEnum.name() + " = " + value;
    }



}