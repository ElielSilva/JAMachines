import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogsService, MachineStatusLogResponseDTO } from '../../core/services/logs.service';

@Component({
  selector: 'app-machines-logs',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './machines-logs.component.html',
  styleUrls: ['./machines-logs.component.scss']
})
export class MachinesLogsComponent implements OnInit {
  private logsService = inject(LogsService);
  logs: MachineStatusLogResponseDTO[] = [];

  ngOnInit(): void {
    this.logsService.getAll().subscribe({
      next: (data) => this.logs = data,
      error: (err) => console.error('Erro ao carregar logs:', err)
    });
  }
}