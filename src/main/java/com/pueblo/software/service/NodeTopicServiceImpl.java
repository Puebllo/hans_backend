package com.pueblo.software.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;
import com.pueblo.software.model.NodeTopic;
import com.pueblo.software.repository.NodeDynamicParametersDefinitionDao;
import com.pueblo.software.repository.NodeTopicDao;

@Service("nodeTopicService")
@Transactional
public class NodeTopicServiceImpl implements NodeTopicService{

	@Autowired
	NodeTopicDao repo;
	
	@PersistenceContext
	EntityManager entityManager;

/*	@Override
	public List<NodeTopic> getNodeTopicsByNode(Node node, boolean excludeNodeDynamicParamaterTopics) {
		return repo.getNodeTopicsByNode(node,excludeNodeDynamicParamaterTopics);
	}

	@Override
	public NodeTopic getNodeTopicByNodeTopicString(String topicName) {
		return repo.getNodeTopicByNodeTopicString(topicName);
	}*/

	
/*	@Autowired
	NodeDynamicParametersDefinitionDao ndpdDao;*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NodeTopic> getNodeTopicsByNode(Node node, boolean excludeNodeDynamicParamaterTopics) {
		List<NodeTopic> toReturn = new ArrayList<>();
		
		Query q = getEm().createQuery("SELECT nt FROM " + NodeTopic.class.getSimpleName() + " nt WHERE nt."+NodeTopic.NODE + " =:node");
		q.setParameter("node", node);
		toReturn = q.getResultList();
		if (excludeNodeDynamicParamaterTopics) {
			List<NodeDynamicParametersDefinition> nodeDynamicParametersDefinitions = getNodeDynamicParametersDefinitionByNode(node);
			toReturn = toReturn.stream()
					.filter(nt -> nodeDynamicParametersDefinitions.stream()
					.noneMatch(ndpd -> nt.getTopic().split("/")[nt.getTopic().split("/").length-1].equals(ndpd.getReservedTopic().getValue())))
					.collect(Collectors.toList());
		}
		return toReturn;
	}

	private EntityManager getEm() {
		return entityManager;
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
		repo.save(nodeTopic);
	}
	
	/* @Override
	 * public void updateShowChart(NodeTopicUpdate nodeTopicUpdate) {
	 * NodeTopic nodeTopic =
	 * dao.genNodeTopicByNodeIdAndTopicId(nodeTopicUpdate.getNodeId(),
	 * nodeTopicUpdate.getTopicId());
	 * nodeTopic.setShowChart(nodeTopicUpdate.showChart);
	 * dao.persistData(nodeTopic);
	 * } */
	
	
	@SuppressWarnings("unchecked")
	public List<NodeDynamicParametersDefinition> getNodeDynamicParametersDefinitionByNode(Node node) {
		Query q = getEm().createQuery("SELECT n FROM " + NodeDynamicParametersDefinition.class.getSimpleName() + " n WHERE n." + NodeDynamicParametersDefinition.NODE+" =:node");
		q.setParameter("node", node);
		return (List<NodeDynamicParametersDefinition>) q.getResultList();
	}
}
