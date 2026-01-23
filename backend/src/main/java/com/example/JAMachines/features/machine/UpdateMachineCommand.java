package com.example.JAMachines.features.machine;

import jakarta.validation.constraints.*;

public record UpdateMachineCommand(
        @NotBlank(message = "Name is required.")
        @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters.")
        @Pattern(
                regexp = "^[A-Za-zÀ-ú\\s]+$",
                message = "Name must contain only letters and spaces."
        )
        String name,

        @NotNull(message = "CPU is required.")
        @Min(value = 1, message = "CPU must be at least 1 core.")
        Integer cpu,

        @NotNull(message = "Memory is required.")
        @Min(value = 512, message = "Memory must be at least 512 MB.")
        Integer memory,

        @NotNull(message = "Disk is required.")
        @Min(value = 1, message = "Disk must be at least 1 GB.")
        Integer disk
) {
}