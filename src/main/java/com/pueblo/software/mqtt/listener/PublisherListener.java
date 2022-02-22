package com.pueblo.software.mqtt.listener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodePayloadReceived;
import com.pueblo.software.model.NodeTopic;
import com.pueblo.software.repository.NodeTopicDao;
import com.pueblo.software.service.NodePayloadReceivedService;
import com.pueblo.software.service.NodeService;
import com.pueblo.software.service.NodeTopicService;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;

@Component
@EnableTransactionManagement
public class PublisherListener extends AbstractInterceptHandler {

	private static final Logger LOG = Logger.getLogger(PublisherListener.class);

	@Autowired
	NodeService nService;

	@Autowired
	NodeTopicService ntService;

/*	@Autowired
	NodeTopicDao ntDao;*/

	@Autowired
	NodePayloadReceivedService nprService;

	HashMap<Node,ArrayList<NodeTopic>> topicsByNodeHM = new HashMap<>();
	HashMap<String,Node> nodeByNodeIdHM = new HashMap<>();
	HashMap<String,NodeTopic> nodeTopicByMessageTopicHM = new HashMap<>();

	ArrayList<NodePayloadReceived> dataToPersist = new ArrayList<>();

	@Override
	public void onPublish(InterceptPublishMessage message) {
		List<String> knowsTopics = getTopicsByNodeId(message.getClientID());
		if (knowsTopics.contains(message.getTopicName())) {
			NodePayloadReceived npr = new NodePayloadReceived();
			npr.setNode(getNodeByNodeId(message.getClientID()));
			npr.setNodeTopic(nodeTopicByMessageTopicHM.get(message.getTopicName()));
			byte[] msg = new byte[message.getPayload().readableBytes()];
			message.getPayload().duplicate().readBytes(msg);
			npr.setPayloadReceived(new String(msg));
			npr.setPayloadDateTime(LocalDateTime.now());
			LOG.info("Created data to persist. Details: Node = " + npr.getNode() +	" Topic = [" + npr.getNodeTopic().getTopic() + "] Data type = " +	npr.getNodeTopic().getNodeDataType()+ " Value = " +	npr.getPayloadReceived());
			dataToPersist.add(npr);
			NodeTopic nt = npr.getNodeTopic();
			nt.setLastNodePayloadReceived(npr);
			ntService.persistData(nt);
		}else {
			LOG.warn("Unknown topic [" + message.getTopicName()+ "] for node: " + message.getClientID());
		}

		//if (dataToPersist.size()==2) {
		nprService.persistData(dataToPersist);
		LOG.info("Data persisted !!");
		dataToPersist.clear();
		//}
	}

	private List<String> getTopicsByNodeId(String clientID) {
		List<String> toReturn = new ArrayList<>();
		List<NodeTopic> ntList = new ArrayList<>();
		Node node = getNodeByNodeId(clientID);
		if (node!=null) {
			if (topicsByNodeHM.containsKey(node)) {
				ntList = topicsByNodeHM.get(node);
			}else {
				ntList = ntService.getNodeTopicsByNode(node,false);
			}
			for (NodeTopic nodeTopic : ntList) {
				toReturn.add(nodeTopic.getTopic());
				nodeTopicByMessageTopicHM.put(nodeTopic.getTopic(), nodeTopic);
			}
		}
		return toReturn;
	}

	private Node getNodeByNodeId(String clientID) {
		if (!nodeByNodeIdHM.containsKey(clientID)) {
			Node node = nService.getNodeByNodeName(clientID);
			nodeByNodeIdHM.put(clientID, node);
			return node;
		}
		return nodeByNodeIdHM.get(clientID);
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}
} 