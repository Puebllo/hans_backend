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
@Table(name="scene",schema="public")
public class Scene implements EntityInterface{


	private static final long serialVersionUID = -879559552788884796L;
	
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String AUTOMATION_AREA = "automationArea";
	public static final String NODE = "node";

	public Long id;
	public Long version;
	public AutomationArea automationArea;
	public Boolean defaultScene;
	public Boolean sceneEnabled;

	   
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
			Scene scene = (Scene) obj;
			if (scene.getId()!=null) {
				if (this.getId().equals(scene.getId())) {
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "automation_area", nullable = false)
	public AutomationArea getAutomationArea() {
		return automationArea;
	}

	public void setAutomationArea(AutomationArea automationArea) {
		this.automationArea = automationArea;
	}

	@Column(name = "default_scene", nullable = false)
	public Boolean getDefaultScene() {
		return defaultScene;
	}

	public void setDefaultScene(Boolean defaultScene) {
		this.defaultScene = defaultScene;
	}

	@Column(name = "scene_enabled", nullable = false)
	public Boolean getSceneEnabled() {
		return sceneEnabled;
	}

	public void setSceneEnabled(Boolean sceneEnabled) {
		this.sceneEnabled = sceneEnabled;
	}
	
	@Override
	public String toString() {
		return this.getAutomationArea() + " isDefault = " + this.getDefaultScene() + " isEnabled = " + this.getSceneEnabled();
	}

}
