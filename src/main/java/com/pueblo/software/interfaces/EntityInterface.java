package com.pueblo.software.interfaces;

import java.io.Serializable;

public interface EntityInterface extends Serializable {
	
	public static final String ID = "id";
    public static final String VERSION = "version";
	
	public void setId(Long id);
	public Long getId();
	public void setVersion(Long version);
	public Long getVersion();

}
