package com.example.JAMachines.features.machine.query;

import com.example.JAMachines.application.common.exceptions.ResourceNotFoundException;
import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.infrestructure.persistence.MachineRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MachineQueryHandler {
    private final MachineRepository machineRepository;

    public MachineQueryHandler(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    public Machine getById(UUID id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));
    }
}
