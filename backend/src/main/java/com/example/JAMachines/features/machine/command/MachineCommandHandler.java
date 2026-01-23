package com.example.JAMachines.features.machine.command;

import com.example.JAMachines.application.common.exceptions.BusinessRuleException;
import com.example.JAMachines.application.common.exceptions.ResourceNotFoundException;
import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.domain.entity.MachineStatus;
import com.example.JAMachines.domain.entity.MachineStatusLog;
import com.example.JAMachines.domain.entity.User;
import com.example.JAMachines.features.machine.CreateMachineCommand;
import com.example.JAMachines.features.machine.MachineResponseDTO;
import com.example.JAMachines.features.machine.UpdateMachineCommand;
import com.example.JAMachines.infrestructure.persistence.MachineRepository;
import com.example.JAMachines.infrestructure.persistence.MachineStatusLogRepository;
import com.example.JAMachines.infrestructure.persistence.UserRepository; // Adicionado
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class MachineCommandHandler {
    private final MachineRepository machineRepository;
    private final MachineStatusLogRepository logRepository;
    private final UserRepository userRepository; // Adicionado para buscar o User do log

    public MachineCommandHandler(
            MachineRepository machineRepository,
            MachineStatusLogRepository logRepository,
            UserRepository userRepository) {
        this.machineRepository = machineRepository;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    public MachineResponseDTO create(CreateMachineCommand command, Principal principal) {
        log.info("info - create machine {} {}", command.name(), command.cpu());
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        long machineCount = machineRepository.countByUser(user);
        if (machineCount >= 5) {
            throw new BusinessRuleException("Limit reached: Each user can have a maximum of 5 machines.");
        }
        Machine machine = Machine.builder()
                .name(command.name())
                .cpu(command.cpu())
                .memory(command.memory())
                .disk(command.disk())
                .machineStatus(MachineStatus.STOP)
                .user(user)
                .build();

        return MachineResponseDTO.from(machineRepository.save(machine));
    }

    public MachineResponseDTO update( UUID id, UpdateMachineCommand command) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));

        machine.setName(command.name());
        machine.setCpu(command.cpu());
        machine.setMemory(command.memory());
        machine.setDisk(command.disk());

        return MachineResponseDTO.from(machineRepository.save(machine));
    }

    @Transactional
    public MachineResponseDTO updateStatus(UUID machineId, MachineStatus newStatus, String email) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        machine.setMachineStatus(newStatus);
        machineRepository.save(machine);

        MachineStatusLog log = MachineStatusLog.builder()
                .machine(machine)
                .user(user)
                .status(newStatus)
                .changedAt(LocalDateTime.now())
                .build();

        logRepository.save(log);

        return MachineResponseDTO.from(machine);
    }

    public void delete(UUID id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found"));

        machineRepository.delete(machine);
    }
}