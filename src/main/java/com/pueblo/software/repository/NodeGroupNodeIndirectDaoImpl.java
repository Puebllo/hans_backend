package com.pueblo.software.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pueblo.software.model.NodeGroup;
import com.pueblo.software.model.NodeGroupNodeIndirect;

@Repository("nodeGroupNodeIndirectDao")
@Transactional
public class NodeGroupNodeIndirectDaoImpl extends AbstractDao<Long, NodeGroupNodeIndirect> implements NodeGroupNodeIndirectDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NodeGroupNodeIndirect> getNodeGroupNodeIndirectByGroup(NodeGroup nodeGroup) {
		Query q = getEm().createQuery("SELECT ng FROM " + NodeGroupNodeIndirect.class.getSimpleName() +" ng WHERE ng." +NodeGroupNodeIndirect.NODE_GROUP + " =:nodeGroup");
		q.setParameter("nodeGroup", nodeGroup);
		return (List<NodeGroupNodeIndirect>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<NodeGroup, List<NodeGroupNodeIndirect>> getNodeGroupNodeIndirectByNodeGroups(List<NodeGroup> nodeGroupsList) {
		 HashMap<NodeGroup, List<NodeGroupNodeIndirect>> toReturn = new HashMap<>();
			Query q = getEm().createQuery("SELECT ng FROM " + NodeGroupNodeIndirect.class.getSimpleName() +" ng WHERE ng." + NodeGroupNodeIndirect.NODE_GROUP+" IN(:groups)");
			q.setParameter("groups", nodeGroupsList);
			for (NodeGroupNodeIndirect ngni : (List<NodeGroupNodeIndirect>)q.getResultList()) {
				List<NodeGroupNodeIndirect> list = new ArrayList<>();
				if (!toReturn.containsKey(ngni.getNodeGroup())) {
					list.add(ngni);
				}else {
					list = toReturn.get(ngni.getNodeGroup());
					list.add(ngni);
				}
				toReturn.put(ngni.nodeGroup, list);
			}
			
		return toReturn;
	}

	@Override
	public void persistData(NodeGroupNodeIndirect ngni) {
		persist(ngni);
	}

}
