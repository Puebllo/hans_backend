package com.pueblo.software.repository;

import java.util.HashMap;
import java.util.List;

import com.pueblo.software.model.NodeGroup;
import com.pueblo.software.model.NodeGroupNodeIndirect;

public interface NodeGroupNodeIndirectDao {

	List<NodeGroupNodeIndirect> getNodeGroupNodeIndirectByGroup(NodeGroup nodeGroup);

	HashMap<NodeGroup, List<NodeGroupNodeIndirect>> getNodeGroupNodeIndirectByNodeGroups(List<NodeGroup> nodeGroupsList);
	
	void persistData(NodeGroupNodeIndirect ngni);

}
