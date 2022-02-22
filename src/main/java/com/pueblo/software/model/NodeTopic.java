package com.pueblo.software.model;

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

import com.pueblo.software.common.CommonMethods;
import com.pueblo.software.enums.NodeDataType;
import com.pueblo.software.interfaces.EntityInterface;

@Entity
@Table(name="node_topic",schema="public", uniqueConstraints= @UniqueConstraint(columnNames = {"node", "topic"}))
public class NodeTopic implements EntityInterface {
	
	private static final long serialVersionUID = -7582179789823764038L;
	

    public static final String NODE = "node";
    public static final String TOPIC = "topic";
    public static final String NODE_DATA_TYPE = "nodeDataType";
    public static final String NUMBER_OF_SAMPLES = "numberOfSamples";
    public static final String SHOW_CHART = "showChart";
    public static final String LAST_NODE_PAYLOAD_RECEIVED = "lastNodePayloadReceived";

    public Long id;
    public Long version;
    public Node node;
    public String topic;
    public NodeDataType nodeDataType;
    public Boolean showChart;
    public Long topicQos;
    public Long numberOfSamples;
    public NodePayloadReceived lastNodePayloadReceived;
    
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

    @Column(name = "topic", nullable = false)
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Enumerated(EnumType.STRING)
    @Column(name = "node_data_type", nullable = false)
	public NodeDataType getNodeDataType() {
		return nodeDataType;
	}
	
	@Transient
	public String getDefaultTopicName() {
		return CommonMethods.getDefaultTopicName(getNodeDataType());
	}
	
	@Transient
	public String getPostfixValue() {
		return CommonMethods.getPostfixValue(getNodeDataType());
	}
	
	@Transient
	public String getJSCanvasDataFormat() {
		return CommonMethods.getJSCanvasDataFormatByDataType(getNodeDataType());
	}

	public void setNodeDataType(NodeDataType nodeDataType) {
		this.nodeDataType = nodeDataType;
	}

    @Column(name = "topic_qos", nullable = false)
	public Long getTopicQos() {
		return topicQos;
	}

	public void setTopicQos(Long topicQos) {
		this.topicQos = topicQos;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj!=null) {
			NodeTopic node = (NodeTopic) obj;
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
		return topic;
	}

    @Column(name = "number_of_samples", nullable = false)
	public Long getNumberOfSamples() {
		return numberOfSamples;
	}

	public void setNumberOfSamples(Long numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}

	@Column(name = "show_chart", nullable = false)
	public Boolean getShowChart() {
		return showChart;
	}

	public void setShowChart(Boolean showChart) {
		this.showChart = showChart;
	}

	@Column(name = "last_node_payload_received")
	public NodePayloadReceived getLastNodePayloadReceived() {
		return lastNodePayloadReceived;
	}

	public void setLastNodePayloadReceived(NodePayloadReceived lastNodePayloadReceived) {
		this.lastNodePayloadReceived = lastNodePayloadReceived;
	}
	

	
}
