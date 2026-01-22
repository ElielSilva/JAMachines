package com.example.JAMachines.features.machine;

import com.example.JAMachines.domain.entity.MachineStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UpdateMachineStatusCommand(

        @NotNull(message = "Status is required.")
        @JsonProperty("status")
        MachineStatus status
) {}
