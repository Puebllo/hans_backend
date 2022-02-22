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
@Table(name="widget",schema="public")
public class Widget implements EntityInterface{

	private static final long serialVersionUID = -8353478495234235990L;
	
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String GRID_DEFINITION = "gridDefinition";
	public static final String X_UNIT_SIZE = "xUnitSize";
	public static final String Y_UNIT_SIZE = "yUnitSize";
	public static final String X_UNIT_POS = "xUnitPos";
	public static final String Y_UNIT_POS = "yUnitPos";
	public static final String BORDER_RADIUS = "borderRadiusPx";
	public static final String WIDGET_COLOR_HEX = "widgetColorHex";

	public Long id;
	public Long version;
	public GridDefinition gridDefinition;
	public Long xUnitSize;
	public Long yUnitSize;
	public Long xUnitPos;
	public Long yUnitPos;
	public Long borderRadiusPx; 
	public String widgetColorHex; 

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
	@JoinColumn(name = "grid_defintion", nullable = false)
	public GridDefinition getGridDefinition() {
		return gridDefinition;
	}

	public void setGridDefinition(GridDefinition gridDefinition) {
		this.gridDefinition = gridDefinition;
	}
	public void setXUnitSize(Long xUnitSize) {
		this.xUnitSize = xUnitSize;
	}

	@Column(name = "x_size_unit", nullable = false)
	public Long getXUnitSize() {
		return xUnitSize;
	}

	public void setDefaultScene(Long xUnitSize) {
		this.xUnitSize = xUnitSize;
	}

	@Column(name = "y_size_unit", nullable = false)
	public Long getYUnitSize() {
		return yUnitSize;
	}

	public void setYUnitSize(Long yUnitSize) {
		this.yUnitSize = yUnitSize;
	}
	
	@Column(name = "x_size_pos", nullable = false)
	public Long getXUnitPos() {
		return xUnitPos;
	}

	public void setXUnitPos(Long xUnitPos) {
		this.xUnitPos = xUnitPos;
	}

	@Column(name = "y_size_pos", nullable = false)
	public Long getYUnitPos() {
		return yUnitPos;
	}

	public void setYUnitPos(Long yUnitPos) {
		this.yUnitPos = yUnitPos;
	}
	
	@Override
	public String toString() {
		return this.getGridDefinition() + " x = " + this.getXUnitSize() + " y = " + this.getYUnitSize();
	}

	@Column(name = "border_radius_px", nullable = false)
	public Long getBorderRadiusPx() {
		return borderRadiusPx;
	}

	public void setBorderRadiusPx(Long borderRadiusPx) {
		this.borderRadiusPx = borderRadiusPx;
	}

	@Column(name = "widget_color_hex", nullable = false)
	public String getWidgetColorHex() {
		return widgetColorHex;
	}

	public void setWidgetColorHex(String widgetColorHex) {
		this.widgetColorHex = widgetColorHex;
	}
}
