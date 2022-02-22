package com.pueblo.software.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pueblo.software.enums.NodeDataType;
import com.pueblo.software.enums.NodeStatusEnum;
import com.pueblo.software.enums.NodeType;
import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;
import com.pueblo.software.model.NodePayloadReceived;
import com.pueblo.software.model.NodeTopic;
import com.pueblo.software.repository.NodeDao;
import com.pueblo.software.repository.NodeDynamicParametersDefinitionDao;
import com.pueblo.software.repository.NodeGroupDao;
import com.pueblo.software.repository.NodeGroupNodeIndirectDao;
import com.pueblo.software.repository.NodePayloadReceivedDao;
import com.pueblo.software.repository.NodeTopicDao;
import com.pueblo.software.repository.UserDao;

@Service("nodeService")
@Transactional
public class NodeServiceImpl implements NodeService{

	@Autowired
	NodeDao dao;
	
	@Autowired
	NodeGroupDao ngDao;
	
	@Autowired
	NodeGroupNodeIndirectDao ngniDao;
	
	@Autowired
	NodeTopicService ntService;
	
/*	@Autowired
	NodeTopicDao ntDao;*/

/*	@Autowired
	NodePayloadReceivedDao nprDao;*/

/*	@Autowired
	NodeDynamicParametersDefinitionDao ndpdDao;*/
	
	@Autowired
	UserDao userDao;

	@Override
	public Node getNodeByNodeName(String nodeId) {
		return dao.getNodeByNodeName(nodeId);
	}

	@Override
	public Node getNodeByNameAndMac(String nodeName, String macAddress) {
		return dao.getNodeByNameAndMac(nodeName, macAddress);
	}

	@Override
	public boolean verifyNode(String nodeName, String user, String passwd) {
		return dao.verifyNode(nodeName, user, passwd);
	}

	@Override
	public Node getNodeById(Long id) {
		return dao.getNodeById(id);
	}

/*	@Override
	public Node getFullNodeInfoById(Long id) {
		Node node =  dao.getNodeById(id);
		List<NodePayloadReceived> lastTopicsDateTimeList = new ArrayList<>();
		HashMap<NodeTopic,NodePayloadReceived> lastPayloadHM = nprDao.getLastValuesHMByNode(node);
		if (lastPayloadHM!=null) {
			for (Map.Entry<NodeTopic,NodePayloadReceived> entryPayloads : lastPayloadHM.entrySet()) {
				NodePayloadReceived npr =  entryPayloads.getValue();
				lastTopicsDateTimeList.add(npr);
				npr.setDataValueTrend(nprDao.calculateDataTrend(entryPayloads.getKey(), npr));
			}
			HashMap<NodeTopic, NodePayloadReceived> lastReceivedPayloadHM = nprDao.getLastValuesHMByNode(node);
			node.setLastReceivedPayloadHM(removePayloadFromReservedTopics(node,lastReceivedPayloadHM));
			node.setNodeDynamicParametersDefinitionHM(getNodeDynamicParametersDefinitionList(node,lastReceivedPayloadHM));
		}
		if (node.getMinimalResponseTimeInSeconds()!=null) {
			node.setNodeStatusEnum(getNodeStatus(node,lastTopicsDateTimeList));
		}

		return node;
	}*/

	private HashMap<NodeTopic, NodePayloadReceived> removePayloadFromReservedTopics(Node node, HashMap<NodeTopic, NodePayloadReceived> lastReceivedPayloadHM) {
		List<NodeDynamicParametersDefinition> nodeDynamicParametersDefinitions = ntService.getNodeDynamicParametersDefinitionByNode(node);
		if (nodeDynamicParametersDefinitions.size()==0) {
			return lastReceivedPayloadHM;
		}
		
		List<NodeTopic> nodeTopics = new ArrayList<>(lastReceivedPayloadHM.keySet());
		List<String> dynamicParameterTopics = nodeDynamicParametersDefinitions.stream().map(ndpd -> ndpd.getReservedTopic().getValue().trim()).collect(Collectors.toList());
		//List<NodeTopic> notParameterTopics = nodeTopics.stream().filter(nt -> nt.getTopic().split("/")[])
		HashSet<NodeTopic> commonTopics = new HashSet<>();
		for (NodeTopic nt : nodeTopics) {
			String[] topicSplitted = nt.getTopic().split("/");
			String topic = topicSplitted[topicSplitted.length-1].trim();
			for (String dynamic : dynamicParameterTopics) {
				if (dynamic.equals(topic)) {
					commonTopics.add(nt);
				}
			}
		}
		
/*		Collections.sort(nodeDynamicParametersDefinitions,NodeDynamicParametersDefinition.DO_SORT_BY_INTERFACE_DESC);
		
		HashMap<NodeTopic, NodePayloadReceived> toReturn = new HashMap<>();

		for (NodeDynamicParametersDefinition nodeDynamicParametersDefinition : nodeDynamicParametersDefinitions) {
			for (NodePayloadReceived npr : lastReceivedPayloadHM.values()) {
				
				String[] topicSplited = npr.getNodeTopic().getTopic().split("/");
				
				if(!nodeDynamicParametersDefinition.getReservedTopic().getValue().equals(topicSplited[topicSplited.length-1]) && !toReturn.containsKey(npr.getNodeTopic())){
					toReturn.put(npr.getNodeTopic(), npr);
					//break;
				}
			}
		}
		
		*/
		HashMap<NodeTopic, NodePayloadReceived> toReturn = new HashMap<>();
		nodeTopics.removeAll(commonTopics);
		for (NodeTopic nodeTopic : nodeTopics) {
			toReturn.put(nodeTopic, lastReceivedPayloadHM.get(nodeTopic));
		}
		
		return toReturn;
	}

	private HashMap<NodeDynamicParametersDefinition,NodePayloadReceived> getNodeDynamicParametersDefinitionList(Node node, HashMap<NodeTopic, NodePayloadReceived> lastReceivedPayloadHM) {
		List<NodeDynamicParametersDefinition> nodeDynamicParametersDefinitions = ntService.getNodeDynamicParametersDefinitionByNode(node);
		Collections.sort(nodeDynamicParametersDefinitions,NodeDynamicParametersDefinition.DO_SORT_BY_INTERFACE_DESC);
		
		HashMap<NodeDynamicParametersDefinition,NodePayloadReceived> toReturn = new HashMap<>();

		for (NodeDynamicParametersDefinition nodeDynamicParametersDefinition : nodeDynamicParametersDefinitions) {
			for (NodePayloadReceived npr : lastReceivedPayloadHM.values()) {
				String[] topicSplited = npr.getNodeTopic().getTopic().split("/");
				if(nodeDynamicParametersDefinition.getReservedTopic().getValue().equals(topicSplited[topicSplited.length-1])){
					toReturn.put(nodeDynamicParametersDefinition, npr);
					break;
				}
			}
		}
		return toReturn;
	}

	private NodeStatusEnum getNodeStatus(Node node, List<NodePayloadReceived> lastTopicsDateTimeList) {
		if (lastTopicsDateTimeList.size()>0) {
			Collections.sort(lastTopicsDateTimeList,NodePayloadReceived.payloadByDataDESCComparator);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime latestNodeResponeDateTime = lastTopicsDateTimeList.get(0).getPayloadDateTime();
			LocalDateTime maxResponseTime = latestNodeResponeDateTime.plusSeconds(node.getMinimalResponseTimeInSeconds());
			if (now.isAfter(latestNodeResponeDateTime) && now.isBefore(maxResponseTime)) {		
				return NodeStatusEnum.ONLINE;
			}else {
				return NodeStatusEnum.OFFLINE;
			}
		}

		return NodeStatusEnum.OFFLINE;
	}

	@Override
	public List<Node> getNodes() {
		return dao.getNodes();
	}

	@Override
	public void persist(Node node) {
		dao.persist(node);
	}

	@Override
	public HashMap<String, Object> getDataForNodeRegister() {
		HashMap<String, Object> toReturn = new HashMap<>();
		toReturn.put("NODE_GROUPS", ngDao.getAllNodeGroups());
		toReturn.put("NODE_TYPES", NodeType.values());
		toReturn.put("NODE_DATA_TYPES", NodeDataType.values());

		return toReturn;
	}

	/* @Override
	 * public ResponseEntity<String> registerNode(NodeRegisterForm nodeRegisterForm,
	 * String userName) {
	 * Node node = nodeRegisterForm.getNode();
	 * String password = new BCryptPasswordEncoder().encode(CommonMethods.PRE_PASS +
	 * node.getPassword()+CommonMethods.POST_PASS);
	 * node.setPassword(password);
	 * node.setRegisterDate(LocalDateTime.now());
	 * node.setUser(userDao.findBySSO("pueblo")); //FIXME: zrobic dzialajace
	 * logowanie !!!
	 * dao.persist(node);
	 * 
	 * NodeGroupNodeIndirect ngni = new NodeGroupNodeIndirect();
	 * ngni.setNode(node);
	 * ngni.setNodeGroup(ngDao.getNodeGroupByName(nodeRegisterForm.getNodeGroup()));
	 * ngniDao.persistData(ngni);
	 * 
	 * for (NodeTopic nodeTopic : nodeRegisterForm.getNodeTopics()) {
	 * nodeTopic.setNode(node);
	 * nodeTopic.setShowChart(true);
	 * ntDao.persistData(nodeTopic);
	 * }
	 * 
	 * return new ResponseEntity(HttpStatus.OK);
	 * } */

}