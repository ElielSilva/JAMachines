package com.example.JAMachines.features.machine;

import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.domain.entity.MachineStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MachineResponseDTO(
        UUID id,
        String name,
        Integer cpu,
        Integer memory,
        Integer disk,
        MachineStatus machineStatus,
        LocalDateTime createdAt
) {
    public static MachineResponseDTO from(Machine entity) {
        return new MachineResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpu(),
                entity.getMemory(),
                entity.getDisk(),
                entity.getMachineStatus(),
                entity.getCreatedAt()
        );
    }
}