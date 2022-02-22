package com.pueblo.software.repository;

import java.util.List;

import com.pueblo.software.model.NodeGroup;

public interface NodeGroupDao {

	List<NodeGroup> getAllNodeGroups();

	NodeGroup getNodeGroupByName(String nodeGroup);

}
