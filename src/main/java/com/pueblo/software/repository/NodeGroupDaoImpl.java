package com.pueblo.software.repository;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pueblo.software.model.NodeGroup;

@Repository("nodeGroupDao")
@Transactional
public class NodeGroupDaoImpl extends AbstractDao<Long,NodeGroup> implements NodeGroupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NodeGroup> getAllNodeGroups() {
		Query q = getEm().createQuery("SELECT ng FROM " + NodeGroup.class.getSimpleName() + " ng");
		return (List<NodeGroup>) q.getResultList();
	}

	@Override
	public NodeGroup getNodeGroupByName(String nodeGroup) {
		Query q = getEm().createQuery("SELECT ng FROM " + NodeGroup.class.getSimpleName() + " ng WHERE ng." + NodeGroup.GROUP_NAME +"=:nodeGroup");
		q.setParameter("nodeGroup", nodeGroup);
		return (NodeGroup) q.getSingleResult();
	}
}
