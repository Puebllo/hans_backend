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
@Table(name="node_group_node_indirect",schema="public")
public class NodeGroupNodeIndirect implements EntityInterface{

	private static final long serialVersionUID = -8200259887947210381L;

	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String NODE_GROUP = "nodeGroup";
	public static final String NODE = "node";

	public Long id;
	public Long version;
	public NodeGroup nodeGroup;
	public Node node;

	   
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
			NodeGroupNodeIndirect nodeGroupNodeIndirect = (NodeGroupNodeIndirect) obj;
			if (nodeGroupNodeIndirect.getId()!=null) {
				if (this.getId().equals(nodeGroupNodeIndirect.getId())) {
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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "node", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "node_group", nullable = false)
	public NodeGroup getNodeGroup() {
		return nodeGroup;
	}

	public void setNodeGroup(NodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}
	
	@Override
	public String toString() {
		return this.getNodeGroup() + " " + this.getNode();
	}
}
