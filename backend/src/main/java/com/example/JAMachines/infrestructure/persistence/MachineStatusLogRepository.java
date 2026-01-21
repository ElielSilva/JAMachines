package com.example.JAMachines.infrestructure.persistence;

import com.example.JAMachines.domain.entity.MachineStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface MachineStatusLogRepository extends JpaRepository<MachineStatusLog, UUID> {
    List<MachineStatusLog> findByMachineId(UUID machineId);
}
