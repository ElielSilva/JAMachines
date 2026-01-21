package com.example.JAMachines.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "machines")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpu", nullable = false)
    private int cpu;

    @Column(name = "memory", nullable = false)
    private int memory;

    @Column(name = "disk", nullable = false)
    private int disk;

    @Column(name = "STATUS", nullable = false)
    private MachineStatus machineStatus;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}

