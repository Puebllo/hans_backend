package com.pueblo.software.repository;

import java.util.List;

import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pueblo.software.common.CommonMethods;
import com.pueblo.software.model.Node;

@Repository("nodeDao")
@Transactional
public class NodeDaoImpl extends AbstractDao<Long, Node> implements NodeDao {

	private static final Logger LOG = Logger.getLogger(NodeDaoImpl.class);
	
	@Override
	public Node getNodeByNodeName(String nodeId) {
		Query q = getEm().createQuery("SELECT n FROM " + Node.class.getSimpleName() + " n WHERE n." + Node.NODE_NAME + " =:nodeId");
		q.setParameter("nodeId", nodeId.split("-")[0]);
		@SuppressWarnings("unchecked")
		List<Node> nodeAl = q.getResultList();
		if (nodeAl.size()==1) {
			return nodeAl.get(0);
		}
		return null;
	}

	@Override
	public boolean verifyNode(String nodeName, String user, String passwd) {
		String[] nodeNameData = nodeName.split("-");
		if (nodeNameData.length==2) {
			String nodeNamee = nodeNameData[0].trim();
			String macAddress = nodeNameData[1].trim();
			Node node = getNodeByNameAndMac(nodeNamee, macAddress);
			if (node!=null) {
				String fullPasswd = CommonMethods.PRE_PASS + passwd + CommonMethods.POST_PASS;
				if (BCrypt.checkpw(fullPasswd, node.getPassword())) {
					LOG.info("Node " + nodeNamee + " verified !");
					return true;
				}
				else {
					LOG.info("Invalid credentials for node " + nodeName + ". Access denied !!!");
					return false;
				}
			}
		}else {
			LOG.info("Wrong nodeName " + nodeName + ". Access denied !!!");
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Node getNodeByNameAndMac(String nodeName, String macAddress) {
		Query q = getEm().createQuery("SELECT n FROM " + Node.class.getSimpleName()+" n WHERE n." + Node.NODE_NAME + " =:nodeName AND n." + Node.NODE_MAC_ADDRESS + " =:macAddress");
		q.setParameter("nodeName", nodeName);
		q.setParameter("macAddress", macAddress);
		List<Node> nodeAl = q.getResultList();
		if (nodeAl.size()==1) {
			return nodeAl.get(0);
		}
		return null;
	}

	@Override
	public Node getNodeById(Long id) {
		return findById(id);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getNodes() {
        Query q = getEm().createQuery("SELECT n FROM " +Node.class.getSimpleName()+" n");
        return q.getResultList();
    }

}
