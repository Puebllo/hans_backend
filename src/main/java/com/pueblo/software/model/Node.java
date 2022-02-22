package com.pueblo.software.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pueblo.software.enums.NodeStatusEnum;
import com.pueblo.software.enums.NodeType;
import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="node",schema="public", uniqueConstraints= { @UniqueConstraint(columnNames = {"node_name"})})
public class Node implements EntityInterface{

	private static final long serialVersionUID = -4522974889523489345L;
	
	public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String NODE_NAME = "nodeName";
    public static final String NODE_MAC_ADDRESS = "nodeMacAddress";
    public static final String NODE_TYPE = "nodeType";
    public static final String NODE_USER = "nodeUser";
    public static final String PASSWORD = "password";
    public static final String REGISTER_DATE = "registerDate";
    public static final String USER = "user";

	public Long id;
    public Long version;
    public String nodeName;
    public String nodeMacAddress;
    public NodeType nodeType;
    public String nodeUser;
    public String password;
    public LocalDateTime registerDate;
    public User user;
    public Long minimalResponseTimeInSeconds;
    
    //Transient
    @JsonIgnore
    private HashMap<NodeTopic,NodePayloadReceived> lastReceivedPayloadHM;
    
    @JsonIgnore
    private NodeStatusEnum nodeStatusEnum;
    
    @JsonIgnore
    private HashMap<NodeDynamicParametersDefinition,NodePayloadReceived> nodeDynamicParametersDefinitionHM;

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
    
  //  @JsonProperty(value = "nodeName")
    @Column(name="node_name", nullable=false)
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String name) {
		this.nodeName = name;
	}

  //  @JsonProperty(value = "nodeUser")
    @Column(name="node_user", nullable=false)
	public String getNodeUser() {
		return nodeUser;
	}

	public void setNodeUser(String user) {
		this.nodeUser = user;
	}

   // @JsonProperty(value = "password")
    @Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @Column(name="register_date", nullable=false)
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

  //  @JsonProperty(value = "nodeType")
	@Column(name="node_type", nullable=false)
	@Enumerated(EnumType.STRING)
	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

 //   @JsonProperty(value = "nodeMacAddress")
	@Column(name="node_mac_address", nullable=false)
	public String getNodeMacAddress() {
		return nodeMacAddress;
	}

	public void setNodeMacAddress(String nodeMacAddress) {
		this.nodeMacAddress = nodeMacAddress;
	}
	
	@Transient
	public HashMap<NodeTopic, NodePayloadReceived> getLastReceivedPayloadHM() {
		return lastReceivedPayloadHM;
	}

	public void setLastReceivedPayloadHM(HashMap<NodeTopic, NodePayloadReceived> lastReceivedPayloadHM) {
		this.lastReceivedPayloadHM = lastReceivedPayloadHM;
	}
	

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "users", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="minimal_response_time_in_seconds")
    public Long getMinimalResponseTimeInSeconds() {
        return minimalResponseTimeInSeconds;
    }

    public void setMinimalResponseTimeInSeconds(Long minimalResponseTimeInSeconds) {
        this.minimalResponseTimeInSeconds = minimalResponseTimeInSeconds;
    }

    @Transient
	public NodeStatusEnum getNodeStatusEnum() {
		return nodeStatusEnum;
	}

	public void setNodeStatusEnum(NodeStatusEnum nodeStatusEnum) {
		this.nodeStatusEnum = nodeStatusEnum;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			Node node = (Node) obj;
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
    	return nodeName + " [" + nodeType.name()+"]";
    }

    @Transient
	public  HashMap<NodeDynamicParametersDefinition,NodePayloadReceived> getNodeDynamicParametersDefinitionHM() {
		return nodeDynamicParametersDefinitionHM;
	}

	public void setNodeDynamicParametersDefinitionHM(HashMap<NodeDynamicParametersDefinition,NodePayloadReceived> nodeDynamicParametersDefinitionHM) {
		this.nodeDynamicParametersDefinitionHM = nodeDynamicParametersDefinitionHM;
	}

}