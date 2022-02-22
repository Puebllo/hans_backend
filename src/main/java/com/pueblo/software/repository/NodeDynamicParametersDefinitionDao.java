package com.pueblo.software.repository;

import java.util.List;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;

public interface NodeDynamicParametersDefinitionDao {

	List<NodeDynamicParametersDefinition> getNodeDynamicParametersDefinitionByNode(Node node);

}
