package com.pueblo.software.repository;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodeDynamicParametersDefinition;

@Repository("nodeDynamicParametersDefinitionDao")
@Transactional
public class NodeDynamicParametersDefinitionDaoImpl extends AbstractDao<Long,NodeDynamicParametersDefinition> implements NodeDynamicParametersDefinitionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NodeDynamicParametersDefinition> getNodeDynamicParametersDefinitionByNode(Node node) {
		Query q = getEm().createQuery("SELECT n FROM " + NodeDynamicParametersDefinition.class.getSimpleName() + " n WHERE n." + NodeDynamicParametersDefinition.NODE+" =:node");
		q.setParameter("node", node);
		return (List<NodeDynamicParametersDefinition>) q.getResultList();
	}

}
