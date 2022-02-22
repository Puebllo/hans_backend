package com.pueblo.software.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;
import com.pueblo.software.model.NodeTopic;

@Repository("nodeTopicDao")
@Transactional
public class NodeTopicDaoImpl extends AbstractDao<Long, NodeTopic> implements NodeTopicDao {

	@Autowired
	NodeDynamicParametersDefinitionDao ndpdDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NodeTopic> getNodeTopicsByNode(Node node, boolean excludeNodeDynamicParamaterTopics) {
		List<NodeTopic> toReturn = new ArrayList<>();
		
		Query q = getEm().createQuery("SELECT nt FROM " + NodeTopic.class.getSimpleName() + " nt WHERE nt."+NodeTopic.NODE + " =:node");
		q.setParameter("node", node);
		toReturn = q.getResultList();
		if (excludeNodeDynamicParamaterTopics) {
			List<NodeDynamicParametersDefinition> nodeDynamicParametersDefinitions = ndpdDao.getNodeDynamicParametersDefinitionByNode(node);
			toReturn = toReturn.stream()
					.filter(nt -> nodeDynamicParametersDefinitions.stream()
					.noneMatch(ndpd -> nt.getTopic().split("/")[nt.getTopic().split("/").length-1].equals(ndpd.getReservedTopic().getValue())))
					.collect(Collectors.toList());
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NodeTopic getNodeTopicByNodeTopicString(String topicName) {
		Query q = getEm().createQuery("SELECT nt FROM " + NodeTopic.class.getSimpleName() + " nt WHERE nt."+NodeTopic.TOPIC + " =:topic");
		q.setParameter("topic", topicName);
		List<NodeTopic> dataList= q.getResultList(); 
		if(dataList.size()==1) {
			return dataList.get(0);
		}
		return null;
	}

	@Override
	public NodeTopic genNodeTopicByNodeIdAndTopicId(Long nodeId, Long topicId) {
		Query q = getEm().createQuery("SELECT nt FROM " + NodeTopic.class.getSimpleName() + " nt WHERE nt."+NodeTopic.ID + " =:topic AND nt." +NodeTopic.NODE + "."+Node.ID+" =:node");
		q.setParameter("topic", topicId);
		q.setParameter("node", nodeId);
		List<NodeTopic> dataList= q.getResultList(); 
		if(dataList.size()==1) {
			return dataList.get(0);
		}
		return null;
	}

	@Override
	public void persistData(NodeTopic nodeTopic) {
		persist(nodeTopic);
	}

	
}
