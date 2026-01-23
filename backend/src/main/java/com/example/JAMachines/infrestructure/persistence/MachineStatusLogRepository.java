package com.example.JAMachines.infrestructure.persistence;

import com.example.JAMachines.domain.entity.MachineStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.List;

public interface MachineStatusLogRepository extends JpaRepository<MachineStatusLog, UUID> {
    List<MachineStatusLog> findByMachineId(UUID machineId);

    @Query("SELECT l FROM MachineStatusLog l WHERE l.machine.user.email = :email")
    List<MachineStatusLog> findAllByUserEmail(@Param("email") String email);
}
