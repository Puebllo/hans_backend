package com.pueblo.software.repository;

import java.util.List;

import com.pueblo.software.model.Node;

public interface NodeDao {

	Node getNodeByNodeName(String nodeId);
	
	Node getNodeByNameAndMac(String nodeName, String macAddress);
	
	boolean verifyNode(String nodeName, String user, String passwd);

	Node getNodeById(Long id);

    List<Node> getNodes();	
    
    void persist(Node node);
}
