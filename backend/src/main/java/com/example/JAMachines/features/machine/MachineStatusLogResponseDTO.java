package com.example.JAMachines.features.machine;

import com.example.JAMachines.domain.entity.MachineStatusLog;

import java.time.LocalDateTime;
import java.util.UUID;

public record MachineStatusLogResponseDTO(
        UUID id,
        String userName,
        String machineName,
        LocalDateTime dateTime,
        String status
) {
    public static MachineStatusLogResponseDTO from(MachineStatusLog log) {
        return new MachineStatusLogResponseDTO(
                log.getId(),
                log.getMachine().getUser().getName(),
                log.getMachine().getName(),
                log.getChangedAt(),
                log.getStatus().toString()
        );
    }
}
