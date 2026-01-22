import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MachinesService, Machine } from '../../core/services/machines.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-machines',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './machines-list.component.html',
  styleUrls: ['./machines-list.component.scss']
})
export class MachinesComponent implements OnInit {

  constructor(private machinesService: MachinesService) {}

  ngOnInit(): void {
    this.machinesService.getAllMachine();
  }

  get machines$(): Observable<Machine[]> {
    return this.machinesService.machines$;
  }
}
