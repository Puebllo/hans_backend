package com.pueblo.software.service;

import java.util.List;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;
import com.pueblo.software.model.NodeTopic;

public interface NodeTopicService {

	List<NodeTopic> getNodeTopicsByNode(Node node, boolean excludeNodeDynamicParamaterTopics);
	
	NodeTopic getNodeTopicByNodeTopicString(String topicName);

	NodeTopic genNodeTopicByNodeIdAndTopicId(Long nodeId, Long topicId);

	void persistData(NodeTopic nodeTopic);
	
	List<NodeDynamicParametersDefinition> getNodeDynamicParametersDefinitionByNode(Node node);

	//void updateShowChart(NodeTopicUpdate nodeTopicUpdate);

}
