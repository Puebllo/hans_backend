package com.pueblo.software.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pueblo.software.common.CommonMethods;
import com.pueblo.software.enums.DataValueTrend;
import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodePayloadReceived;
import com.pueblo.software.model.NodeTopic;
import com.pueblo.software.repository.NodePayloadReceivedDao;
import com.pueblo.software.repository.NodePayloadReceivedJPARepository;

@Service("nodePayloadReceivedService")
public class NodePayloadReceivedServiceImpl implements NodePayloadReceivedService {

/*	@Autowired
	NodePayloadReceivedDao dao;*/
	
	@Autowired
	NodePayloadReceivedJPARepository repo;
	
	@PersistenceContext
	EntityManager entityManager;

/*	@Override
	public void persistData(List<NodePayloadReceived> dataList) {
		repo.persistData(dataList);
	}*/


	
	private static final Logger LOG = Logger.getLogger(NodePayloadReceivedServiceImpl.class);

	private HashMap<Node,HashMap<NodeTopic,NodePayloadReceived>> lastValueByNodeHM = new HashMap<>();
	private HashMap<NodeTopic,List<NodePayloadReceived>> previousDataByTopicHM = new HashMap<>();
	
	@Autowired
	NodeTopicService ntService;
	
	@Override
	public void persistData(List<NodePayloadReceived> dataList) {
		for (NodePayloadReceived nodePayloadReceived : dataList) {
			if (lastValueByNodeHM.get(nodePayloadReceived.getNode())==null) {
				HashMap<NodeTopic,NodePayloadReceived> valueByTopicHM = new HashMap<>();
				valueByTopicHM.put(nodePayloadReceived.getNodeTopic(), nodePayloadReceived);
				lastValueByNodeHM.put(nodePayloadReceived.getNode(), valueByTopicHM);
			}else {
				HashMap<NodeTopic,NodePayloadReceived> valueByTopicHM = lastValueByNodeHM.get(nodePayloadReceived.getNode());
				collectPreviousData(valueByTopicHM, nodePayloadReceived);
				valueByTopicHM.put(nodePayloadReceived.getNodeTopic(), nodePayloadReceived);
				lastValueByNodeHM.put(nodePayloadReceived.getNode(), valueByTopicHM);
			}
			repo.save(nodePayloadReceived);
		}
	}

	private void collectPreviousData(HashMap<NodeTopic, NodePayloadReceived> valueByTopicHM,NodePayloadReceived nodePayloadReceived) {
		NodeTopic nodeTopic = nodePayloadReceived.getNodeTopic();
		if (nodeTopic.getNumberOfSamples()!=null && nodeTopic.getNumberOfSamples()!=0 && valueByTopicHM.get(nodeTopic)!=null) {
			List<NodePayloadReceived> nprList = new ArrayList<>();
			if (previousDataByTopicHM.get(nodeTopic)!=null) {
				nprList = previousDataByTopicHM.get(nodeTopic);
			}
			if (nprList.size()==nodeTopic.getNumberOfSamples()) {
				Collections.sort(nprList, NodePayloadReceived.payloadByDataDESCComparator);
				nprList.set(nprList.size()-1, valueByTopicHM.get(nodeTopic));
			}else {
				nprList.add(valueByTopicHM.get(nodeTopic));
			}
			previousDataByTopicHM.put(nodeTopic, nprList);
		}		
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<NodeTopic,LinkedHashMap<String,String>> getChartDataForNode(Node node, LocalDateTime dataFrom, LocalDateTime dataTo) {
		HashMap<NodeTopic,LinkedHashMap<String,String>> toReturn = new LinkedHashMap<>();
		List<NodeTopic> topics = ntService.getNodeTopicsByNode(node,true);
		for (NodeTopic nodeTopic : topics) {
		//	Long startTime = System.currentTimeMillis();
					Query q = getEm().createQuery("SELECT n."+NodePayloadReceived.PAYLOAD_RECEIVED+" , n."+NodePayloadReceived.PAYLOAD_DATE_TIME+" FROM " + NodePayloadReceived.class.getSimpleName() + " n WHERE n." + NodePayloadReceived.NODE+ " =:node AND n."+NodePayloadReceived.NODE_TOPIC+" =:nodeTopic AND (n."+ NodePayloadReceived.PAYLOAD_DATE_TIME + " BETWEEN :dataFrom AND :dataTo) ORDER BY n." +NodePayloadReceived.ID +" ASC");
			q.setParameter("node", node);
			q.setParameter("nodeTopic", nodeTopic);
			q.setParameter("dataFrom", dataFrom);
			q.setParameter("dataTo", dataTo);
			List<Object[]> data = q.getResultList();
		//	Long stopTime = System.currentTimeMillis();
		//	Long delta = stopTime-startTime;
			//LOG.info("query executed in: " + delta + " ns");
		//	ArrayList<NodePayloadReceived> data = (ArrayList<NodePayloadReceived>)q.getResultList();
			//Collections.sort(data, NodePayloadReceived.sortById);		
			LinkedHashMap<String,String> dataLHM= new LinkedHashMap<>();
			for (Object[] npr : data) {
				//if (Double.parseDouble(npr.getPayloadReceived())!=-127) {
				dataLHM.put(CommonMethods.formatCanvasjsLocalDateTime((LocalDateTime)npr[1]), ((String)npr[0]).trim());

					//dataLHM.put(CommonMethods.formatCanvasjsLocalDateTime(npr.getPayloadDateTime()).trim(), npr.getPayloadReceived());
				//}
			}
			toReturn.put(nodeTopic,dataLHM);
		}


		return toReturn;
	}
	
	private EntityManager getEm() {
		return entityManager;
	}

	//FIXME: zoptymalizowac !!!! (problem z nie odswiezaniem node topic)
	public HashMap<NodeTopic,NodePayloadReceived> getLastValuesHMByNode(Node node){
		lastValueByNodeHM = new HashMap<>();
		//if (lastValueByNodeHM.get(node)==null) {
			lastValueByNodeHM.put(node,getLastValuesFromDbByNode(node));
		//}
		return lastValueByNodeHM.get(node);

	}
	
private HashMap<NodeTopic, NodePayloadReceived> getLastValuesFromDbByNode(Node node) {
	HashMap<NodeTopic, NodePayloadReceived> toReturn = new HashMap<>();
	List<NodeTopic> nodeTopics = ntService.getNodeTopicsByNode(node,false);
	for (NodeTopic nodeTopic : nodeTopics) {
		Query q = getEm().createQuery("SELECT n FROM " + NodePayloadReceived.class.getSimpleName() + " n WHERE n." + NodePayloadReceived.NODE_TOPIC+ " =:topic ORDER BY n."+NodePayloadReceived.PAYLOAD_DATE_TIME+" DESC");
		q.setParameter("topic", nodeTopic);
		q.setMaxResults(1);
		if (q.getResultList().size()>0) {
			toReturn.put(nodeTopic, (NodePayloadReceived) q.getResultList().get(0));
		}
	}
		return toReturn;
	}

/*	public NodePayloadReceived getLastValueByNodeAndTopic(Node node, NodeTopic nodeTopic) {
		HashMap<NodeTopic,NodePayloadReceived> dataByTopicsHM = getLastValuesHMByNode(node);
		if (dataByTopicsHM!=null) {
			return dataByTopicsHM.get(nodeTopic);
		}
		return null;
	}*/
	
	public DataValueTrend calculateDataTrend(NodeTopic nodeTopic, NodePayloadReceived actualNpr) {
		if (actualNpr!=null && nodeTopic.getNumberOfSamples()!=null) {
			List<NodePayloadReceived> samplesList = previousDataByTopicHM.get(nodeTopic);
			if (samplesList==null) {
				samplesList = getLastNodePayloadReceivedSamples(nodeTopic,nodeTopic.getNumberOfSamples(),null);
			}
			if (samplesList.size()<nodeTopic.getNumberOfSamples()) {
				long difference = nodeTopic.getNumberOfSamples()-samplesList.size();
				samplesList.addAll(getLastNodePayloadReceivedSamples(nodeTopic, difference,samplesList));
			}
			Double summaryValue = 0.0;
			if (samplesList!=null && samplesList.size()==nodeTopic.getNumberOfSamples()) {
				for (NodePayloadReceived npr : samplesList) {
					try {
						summaryValue += Double.parseDouble(npr.getPayloadReceived());
					} catch (NumberFormatException e) {
						LOG.error("Error while converting samples data to number -  NodePayloadReceived id: " + npr.getId() + " value to convert: " + npr.getPayloadReceived(), e);
						return null;
					}
				}
				Double samplesValue = summaryValue/samplesList.size();
				Double actualValue = 0.0;
				try {
				actualValue = Double.parseDouble(actualNpr.getPayloadReceived());
				} catch (NumberFormatException e) {
					LOG.error("Error while converting actual data to number -  NodePayloadReceived id: " + actualNpr.getId() + " value to convert: " + actualNpr.getPayloadReceived(), e);
					return null;
				}
				if (!samplesValue.isInfinite() && !samplesValue.isNaN()) {
					if (samplesValue!=actualValue) {
						if (actualValue > samplesValue) {
							return DataValueTrend.UP;
						}
						else {
							return DataValueTrend.DOWN;
						}
					}else {
						return DataValueTrend.EQUAL;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<NodePayloadReceived> getLastNodePayloadReceivedSamples(NodeTopic nodeTopic, Long numberOfSamples, List<NodePayloadReceived> samplesAlreadyCached) {
		String s = "SELECT n FROM " + NodePayloadReceived.class.getSimpleName() + " n WHERE n." + NodePayloadReceived.NODE_TOPIC+ " =:nodeTopic";
		if (samplesAlreadyCached!=null) {
			s+= " AND n NOT IN(:cache)";
		}
		s+= " ORDER BY n."+ NodePayloadReceived.PAYLOAD_DATE_TIME + " DESC";
		Query q = getEm().createQuery(s);
		q.setParameter("nodeTopic", nodeTopic);
		if (samplesAlreadyCached!=null) {
			q.setParameter("cache", samplesAlreadyCached);
		}
		q.setMaxResults(numberOfSamples.intValue());
		return q.getResultList();
	}

    @Override
    public List<NodePayloadReceived> getDataByNodeTopicAndDateRange(NodeTopic nodeTopic, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Query q = getEm().createQuery("SELECT n FROM " + NodePayloadReceived.class.getSimpleName() + " n WHERE n." + NodePayloadReceived.NODE_TOPIC+ " =:nodeTopic AND (n."+ NodePayloadReceived.PAYLOAD_DATE_TIME + " BETWEEN :dataFrom AND :dataTo)");
        q.setParameter("nodeTopic", nodeTopic);
        q.setParameter("dataFrom", dateTimeFrom);
        q.setParameter("dataTo", dateTimeTo);

        return (ArrayList<NodePayloadReceived>)q.getResultList();
    }

}
