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
@Table(name="node_group",schema="public")
public class NodeGroup implements EntityInterface{

	private static final long serialVersionUID = -8200259887947210381L;

	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String GROUP_NAME = "groupName";
	public static final String GROUP_DESCRIPTION = "groupDescription";
	public static final String NODE = "node";


	public Long id;
	public Long version;
	public String groupName;
	public String groupDescription;

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
			NodeGroup nodeGroup = (NodeGroup) obj;
			if (nodeGroup.getId()!=null) {
				if (this.getId().equals(nodeGroup.getId())) {
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

	@Column(name = "group_name", nullable = false)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "group_description")
	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	@Override
	public String toString() {
		return groupName;
	}
}
