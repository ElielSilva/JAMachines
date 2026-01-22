import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MachinesService, Machine } from '../../core/services/machines.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-machines',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './machines-list.component.html',
  styleUrls: ['./machines-list.component.scss']
})
export class MachinesComponent implements OnInit {
  
  editState: { [key: string]: boolean } = {};

  constructor(private machinesService: MachinesService) {}

  ngOnInit(): void {
    this.machinesService.getAllMachine();
  }

  get machines$(): Observable<Machine[]> {
    return this.machinesService.machines$;
  }

  toggleEdit(id: string, isEditing: boolean): void {
    this.editState[id] = isEditing;
  }

  onSave(machine: Machine): void {
    if (machine.cpu < 1) machine.cpu = 1;
    if (machine.memory < 1) machine.memory = 1;
    if (machine.disk < 1) machine.disk = 1;

    this.machinesService.updateMachine(machine).subscribe({
      next: () => {
        this.toggleEdit(machine.id, false);
      },
      error: (err) => alert('Erro ao salvar alterações.')
    });
  }

  onDelete(id: string): void {
    if (confirm('Deseja remover esta máquina?')) {
      this.machinesService.deleteMachine(id).subscribe();
    }
  }

  onStatusChange(id: string, status: string): void {
    this.machinesService.patchStatus(id, status).subscribe({
      next: () => console.log(`Status alterado para ${status}`),
      error: (err) => alert('Erro ao alterar status da máquina.')
    });
  }

  openCreateModal(): void {
    const name = prompt('Nome da Máquina:');
    if (name) {
      const newMachine: Omit<Machine, 'id' | 'createdAt'> = {
        name,
        cpu: 1,
        memory: 4,
        disk: 20,
        machineStatus: 'stop'
      };
      this.machinesService.createMachine(newMachine as any).subscribe();
    }
  }

  openEditModal(machine: Machine): void {
    const newName = prompt('Novo nome da máquina:', machine.name);
    if (newName && newName !== machine.name) {
      const updatedMachine = { ...machine, name: newName };
      this.machinesService.updateMachine(updatedMachine).subscribe();
    }
  }

  setStatus(id: string, status: string): void {
    this.machinesService.getMachineById(Number(id)).subscribe(machine => {
      const updated = { ...machine, machineStatus: status };
      this.machinesService.updateMachine(updated).subscribe();
    });
  }
}
