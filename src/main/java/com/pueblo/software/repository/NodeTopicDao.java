package com.pueblo.software.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pueblo.software.model.NodeDynamicParametersDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeTopic;

import javax.persistence.Query;

@Repository
public interface NodeTopicDao{

    public List<NodeTopic> getNodeTopicsByNode(Node node, boolean excludeNodeDynamicParamaterTopics);

    public NodeTopic getNodeTopicByNodeTopicString(String topicName);

    public NodeTopic genNodeTopicByNodeIdAndTopicId(Long nodeId, Long topicId);

    public void persistData(NodeTopic nodeTopic);

}
