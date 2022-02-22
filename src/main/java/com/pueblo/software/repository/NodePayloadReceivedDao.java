package com.pueblo.software.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.pueblo.software.enums.DataValueTrend;
import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodePayloadReceived;
import com.pueblo.software.model.NodeTopic;

public interface NodePayloadReceivedDao {

	void persistData(List<NodePayloadReceived> dataList);

	HashMap<NodeTopic,LinkedHashMap<String,String>> getChartDataForNode(Node node, LocalDateTime dataFrom, LocalDateTime dataTo);
	
	HashMap<NodeTopic,NodePayloadReceived> getLastValuesHMByNode(Node node);
	
	DataValueTrend calculateDataTrend(NodeTopic nodeTopic, NodePayloadReceived actualNpr);

    List<NodePayloadReceived> getDataByNodeTopicAndDateRange(NodeTopic nodeTopic, LocalDateTime dateTimeFrom ,LocalDateTime dateTimeTo);

}
