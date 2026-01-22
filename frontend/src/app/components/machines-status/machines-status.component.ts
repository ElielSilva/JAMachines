import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachinesService } from '../../core/services/machines.service';
import { ChartData, ChartOptions, Chart } from 'chart.js';
import { BarController, BarElement, CategoryScale, LinearScale, Tooltip, Legend } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

Chart.register(BarController, BarElement, CategoryScale, LinearScale, Tooltip, Legend);

@Component({
  selector: 'app-machines-status',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './machines-status.component.html'
})
export class MachinesStatusComponent implements OnInit {
  

  public barChartType: 'bar' = 'bar'; 

  public barChartData: ChartData<'bar'> = {
    labels: ['Start', 'Stop', 'Suspend'],
    datasets: [{ data: [0, 0, 0], backgroundColor: ['#4caf50', '#f44336', '#ff9800'] }]
  };

  public barChartOptions: ChartOptions<'bar'> = {
    indexAxis: 'y',
    responsive: true,
  };

  constructor(private machinesService: MachinesService) {}

  ngOnInit(): void {
    this.machinesService.machines$.subscribe(machines => {
      const start = machines.filter(m => m.machineStatus.toLowerCase() === 'start').length;
      const stop = machines.filter(m => m.machineStatus.toLowerCase() === 'stop').length;
      const suspend = machines.filter(m => m.machineStatus.toLowerCase() === 'suspend').length;

      this.barChartData = {
        ...this.barChartData,
        datasets: [{ ...this.barChartData.datasets[0], data: [start, stop, suspend] }]
      };
    });
  }
}