package com.example.JAMachines.features.machine;

import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.domain.entity.MachineStatus;
import com.example.JAMachines.features.machine.command.MachineCommandHandler;
import com.example.JAMachines.features.machine.query.MachineQueryHandler;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/machine")
public class MachineController {
    private final MachineQueryHandler machineQueryHandler;
    private final MachineCommandHandler machineCommandHandler;

    public MachineController(MachineQueryHandler machineQueryHandler, MachineCommandHandler machineCommandHandler) {
        this.machineQueryHandler = machineQueryHandler;
        this.machineCommandHandler = machineCommandHandler;
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping()
    public ResponseEntity<List<MachineResponseDTO>> getAll() {
        return ResponseEntity.ok(machineQueryHandler.getAll());
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/id/{machineId}")
    public ResponseEntity<Machine> get(@PathVariable UUID id) {
        return ResponseEntity.ok(machineQueryHandler.getById(id));
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping
    public ResponseEntity<MachineResponseDTO> create(
            @RequestBody @Valid CreateMachineCommand command,
            Principal principal
    ) {
        MachineResponseDTO machine = machineCommandHandler.create(command, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(machine);
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping("/{id}")
    public ResponseEntity<MachineResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateMachineCommand command
    ) {
        MachineResponseDTO machine = machineCommandHandler.update(id, command);
        return ResponseEntity.ok(machine);
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @PatchMapping("/{id}/status")
    public ResponseEntity<MachineResponseDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMachineStatusCommand command,
            Principal principal
    ) {
        MachineResponseDTO updatedMachine = machineCommandHandler.updateStatus(id, command.status(), principal.getName());
        return ResponseEntity.ok(updatedMachine);
    }

    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        machineCommandHandler.delete(id);
        return ResponseEntity.noContent().build();
    }
}
