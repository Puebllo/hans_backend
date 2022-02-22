package com.pueblo.software.model;

import java.util.Comparator;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.pueblo.software.common.CommonMethods;
import com.pueblo.software.interfaces.EntityInterface;
import com.pueblo.software.interfaces.Orderable;

@Entity
@Table(name="node_dynamic_parameters_definition",schema="public", uniqueConstraints= { @UniqueConstraint(columnNames = {"node","reserved_topic"})})
public class NodeDynamicParametersDefinition implements EntityInterface, Orderable{


	private static final long serialVersionUID = 6050952731236553956L;
	
	public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String RESERVED_TOPIC_ENUM = "reservedTopic";
    public static final String VALUE = "value";
	public static final String NODE = "node";


	public Long id;
    public Long version;
	public Node node;
    public ReservedTopic reservedTopic;
    public Long sort;
    public String parameterIconUrl;

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
    
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "node", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
    
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "reserved_topic", nullable = false)
	public ReservedTopic getReservedTopic() {
		return reservedTopic;
	}

	public void setReservedTopic(ReservedTopic reservedTopic) {
		this.reservedTopic = reservedTopic;
	}
    
	
	@Transient
	public String getParameterName() {
		return CommonMethods.getNodeParameterNameByReservedTopicEnum(reservedTopic.getReservedTopicEnum());
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			NodeDynamicParametersDefinition node = (NodeDynamicParametersDefinition) obj;
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
    	return node.getNodeName() + " = [" + reservedTopic+"]";
    }

	@Override
	public void setSort(Long sort) {
		this.sort=sort;
	}

	@Override
    @Column(name = "sort")
	public Long getSort() {
		return sort;
	}
	
	public static final Comparator<NodeDynamicParametersDefinition> DO_SORT_BY_INTERFACE_DESC = new Comparator<NodeDynamicParametersDefinition>() {
		
		@Override
		public int compare(NodeDynamicParametersDefinition o1, NodeDynamicParametersDefinition o2) {
			if (o1.getSort()!=null && o2.getSort()!=null) {
				o2.getSort().compareTo(o1.getSort());
			}
			return 0;
		}
	};
	
    @Column(name = "parameter_icon_url")
	public String getParameterIconUrl() {
		return parameterIconUrl;
	}

	public void setParameterIconUrl(String parameterIconUrl) {
		this.parameterIconUrl = parameterIconUrl;
	}

}