package com.pueblo.software.service;

import java.util.HashMap;
import java.util.List;

import com.pueblo.software.model.Node;

public interface NodeService {
	
	Node getNodeByNodeName(String nodeId);
	
	Node getNodeByNameAndMac(String nodeName, String macAddress);
	
	boolean verifyNode(String nodeName, String user, String passwd);

	Node getNodeById(Long id);

//	Node getFullNodeInfoById(Long id);

    List<Node> getNodes();
    
    void persist(Node node);

	HashMap<String, Object> getDataForNodeRegister();
	
	//ResponseEntity<String> registerNode(NodeRegisterForm nodeRegisterForm, String string);


}
