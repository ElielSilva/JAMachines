package com.example.JAMachines.features.machine.query;

import com.example.JAMachines.application.common.exceptions.ResourceNotFoundException;
import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.features.machine.MachineResponseDTO;
import com.example.JAMachines.features.machine.MachineStatusLogResponseDTO;
import com.example.JAMachines.infrestructure.persistence.MachineRepository;
import com.example.JAMachines.infrestructure.persistence.MachineStatusLogRepository;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Component
public class MachineQueryHandler {
    private final MachineRepository machineRepository;
    private final MachineStatusLogRepository machineStatusLogRepository;

    public MachineQueryHandler(MachineRepository machineRepository, MachineStatusLogRepository machineStatusLogRepository) {
        this.machineRepository = machineRepository;
        this.machineStatusLogRepository = machineStatusLogRepository;
    }

    public List<MachineResponseDTO> getAll(Principal principal) {
        return machineRepository.findAllByUserEmail(principal.getName())
                .stream()
                .map(MachineResponseDTO::from)
                .toList();
    }

    public Machine getById(UUID id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));
    }

    public List<MachineStatusLogResponseDTO> getAllLogs(Principal principal) {
        return machineStatusLogRepository.findAllByUserEmail(principal.getName())
                .stream()
                .map(MachineStatusLogResponseDTO::from)
                .toList();
    }
}
