package com.example.JAMachines.features.machine;

import com.example.JAMachines.domain.entity.Machine;
import com.example.JAMachines.domain.entity.MachineStatus;
import com.example.JAMachines.features.machine.command.MachineCommandHandler;
import com.example.JAMachines.features.machine.query.MachineQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public ResponseEntity<List<Machine>> getAll() {
        return ResponseEntity.ok(machineQueryHandler.getAll());
    }

    @GetMapping("/id/{machineId}")
    public ResponseEntity<Machine> get(@PathVariable UUID id) {
        return ResponseEntity.ok(machineQueryHandler.getById(id));
    }

    @PostMapping
    public ResponseEntity<Machine> create(
            @RequestBody @Valid CreateMachineCommand command
    ) {
        Machine machine = machineCommandHandler.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(machine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Machine> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateMachineCommand command
    ) {
        Machine machine = machineCommandHandler.update(id, command);
        return ResponseEntity.ok(machine);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Machine> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMachineStatusCommand command,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        Machine updatedMachine = machineCommandHandler.updateStatus(id, command.status(), userId);
        return ResponseEntity.ok(updatedMachine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        machineCommandHandler.delete(id);
        return ResponseEntity.noContent().build();
    }
}
