package com.pueblo.software.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.pueblo.software.model.Node;
import com.pueblo.software.model.NodePayloadReceived;
import com.pueblo.software.model.NodeTopic;

public interface NodePayloadReceivedService {

	void persistData(List<NodePayloadReceived> dataList);
	
	HashMap<NodeTopic,LinkedHashMap<String,String>> getChartDataForNode(Node node, LocalDateTime dataFrom, LocalDateTime dataTo);
	
   List<NodePayloadReceived> getDataByNodeTopicAndDateRange(NodeTopic nodeTopic, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

}
