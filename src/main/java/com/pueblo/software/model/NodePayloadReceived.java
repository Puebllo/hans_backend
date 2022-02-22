package com.pueblo.software.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

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
import javax.persistence.Version;

import com.pueblo.software.common.CommonMethods;
import com.pueblo.software.enums.DataValueTrend;
import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="node_payload_received",schema="public")
public class NodePayloadReceived implements EntityInterface {
	
	private static final long serialVersionUID = -7582179789823764038L;
	
	public static final String ID = "id";
    public static final String VERSION = "version";
    public static final String NODE = "node";
    public static final String NODE_TOPIC = "nodeTopic";
    public static final String PAYLOAD_RECEIVED = "payloadReceived";
    public static final String PAYLOAD_DATE_TIME = "payloadDateTime";

    public Long id;
    public Long version;
    public Node node;
    public NodeTopic nodeTopic;
    public LocalDateTime payloadDateTime;
    public String payloadReceived;
    
    //transient
    public DataValueTrend dataValueTrend;
    
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
	@JoinColumn(name = "node_topic", nullable = false)
	public NodeTopic getNodeTopic() {
		return nodeTopic;
	}

	public void setNodeTopic(NodeTopic nodeTopic) {
		this.nodeTopic = nodeTopic;
	}

    @Column(name = "payload_received", nullable = false)
	public String getPayloadReceived() {
		return payloadReceived;
	}

	public void setPayloadReceived(String payloadReceived) {
		this.payloadReceived = payloadReceived;
	}

	@Transient
	public String getPostfixValue() {
		return CommonMethods.getPostfixValue(getNodeTopic().getNodeDataType());
	}
	
    @Column(name = "payload_date_time", nullable = false)
	public LocalDateTime getPayloadDateTime() {
		return payloadDateTime;
	}
    
    @Transient
    public String getFormatPayloadDateTime() {
    	if (getPayloadDateTime().toLocalDate().equals(LocalDate.now())) {
			return CommonMethods.formatLocalDateTimeToTime(getPayloadDateTime());
		}else{
	    	return CommonMethods.formatLocalDateTimeToDateTime(getPayloadDateTime());
		}
    }

	public void setPayloadDateTime(LocalDateTime payloadDateTime) {
		this.payloadDateTime = payloadDateTime;
	}
	
	@Override
	public String toString() {
		return node + ", " + nodeTopic+": "+ payloadReceived;
	}
	
	public static Comparator<NodePayloadReceived>  sortById = new Comparator<NodePayloadReceived>() {
		@Override
		public int compare(NodePayloadReceived o1, NodePayloadReceived o2) {
			return o1.getId().compareTo(o2.getId());
		}
	};
	
	public static Comparator<NodePayloadReceived> payloadByDataDESCComparator = new Comparator<NodePayloadReceived>() {
		@Override
		public int compare(NodePayloadReceived o1, NodePayloadReceived o2) {
			return o2.getPayloadDateTime().compareTo(o1.getPayloadDateTime());
		}
	};

	@Transient
	public DataValueTrend getDataValueTrend() {
		return dataValueTrend;
	}

	public void setDataValueTrend(DataValueTrend dataValueTrend) {
		this.dataValueTrend = dataValueTrend;
	} 



}
