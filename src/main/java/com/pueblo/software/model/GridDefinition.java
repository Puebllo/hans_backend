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
@Table(name="grid_definition",schema="public")
public class GridDefinition implements EntityInterface{

	private static final long serialVersionUID = -8200259887947210381L;

	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String SCENE = "scene";
	public static final String X_UNIT_SIZE = "xUnitSize";
	public static final String Y_UNIT_SIZE = "yUnitSize";

	public Long id;
	public Long version;
	public Scene scene;
	public Long xUnitSize;
	public Long yUnitSize;

	   
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
			GridDefinition scene = (GridDefinition) obj;
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
	@JoinColumn(name = "scene", nullable = false)
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	@Column(name = "x_size_unit", nullable = false)
	public Long getXUnitSize() {
		return xUnitSize;
	}

	public void setXUnitSize(Long xUnitSize) {
		this.xUnitSize = xUnitSize;
	}

	@Column(name = "y_size_unit", nullable = false)
	public Long getYUnitSize() {
		return yUnitSize;
	}

	public void setYUnitSize(Long yUnitSize) {
		this.yUnitSize = yUnitSize;
	}
	
	@Override
	public String toString() {
		return this.getScene() + " x = " + this.getXUnitSize() + " y = " + this.getYUnitSize();
	}
}
