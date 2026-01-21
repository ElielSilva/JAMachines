package com.example.JAMachines.features.machine.command;

import com.example.JAMachines.application.common.exceptions.ResourceNotFoundException;
import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.domain.entity.MachineStatus;
import com.example.JAMachines.domain.entity.MachineStatusLog;
import com.example.JAMachines.features.machine.CreateMachineCommand;
import com.example.JAMachines.features.machine.UpdateMachineCommand;
import com.example.JAMachines.infrestructure.persistence.MachineRepository;
import com.example.JAMachines.infrestructure.persistence.MachineStatusLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MachineCommandHandler {
    private final MachineRepository machineRepository;
    private final MachineStatusLogRepository logRepository;

    public MachineCommandHandler(MachineRepository machineRepository, MachineStatusLogRepository logRepository) {
        this.machineRepository = machineRepository;
        this.logRepository = logRepository;
    }

    public Machine create(CreateMachineCommand command) {
        Machine machine = Machine.builder()
                .name(command.name())
                .cpu(command.cpu())
                .memory(command.memory())
                .disk(command.disk())
                .machineStatus(MachineStatus.STOP)
                .createdAt(LocalDateTime.now())
                .build();

        return machineRepository.save(machine);
    }

    public Machine update(UUID id, UpdateMachineCommand command) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Machine not found")
                );

        machine.setName(command.name());
        machine.setCpu(command.cpu());
        machine.setMemory(command.memory());

        return machineRepository.save(machine);
    }

    public Machine updateStatus(UUID machineId, MachineStatus newStatus, UUID userId) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));

        machine.setMachineStatus(newStatus);
        machineRepository.save(machine);

        MachineStatusLog log = MachineStatusLog.builder()
                .machineId(machineId)
                .status(newStatus)
                .userId(userId)
                .build();
        logRepository.save(log);

        return machine;
    }

    public void delete(UUID id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Machine not found")
                );

        machineRepository.delete(machine);
    }
}
