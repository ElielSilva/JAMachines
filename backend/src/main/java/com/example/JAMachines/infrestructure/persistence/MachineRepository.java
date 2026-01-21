package com.example.JAMachines.infrestructure.persistence;

import com.example.JAMachines.domain.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MachineRepository extends JpaRepository<Machine, UUID> {
}
