package com.pueblo.software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pueblo.software.model.NodePayloadReceived;

@Repository
public interface NodePayloadReceivedJPARepository extends JpaRepository<NodePayloadReceived, Long>{

}
