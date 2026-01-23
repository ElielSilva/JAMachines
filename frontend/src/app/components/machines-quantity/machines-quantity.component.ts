import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachinesService } from '../../core/services/machines.service';
// import { ChartOptions, ChartData, ChartType } from 'chart.js';
import { ChartData, ChartOptions, ChartType, ChartTypeRegistry } from 'chart.js';
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js';

import { BaseChartDirective } from 'ng2-charts';


Chart.register(PieController, ArcElement, Tooltip, Legend);

@Component({
  selector: 'app-machines-quantity',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './machines-quantity.component.html',
  styleUrls: ['./machines-quantity.component.scss']
})
export class MachinesQuantityComponent implements OnInit {

  quantity = 0;
  
  public pieChartData: ChartData<'pie', number[], string | string[]> = {
    labels: ['Quantidade de Máquinas', 'Restante'],
    datasets: [{ data: [0, 5], backgroundColor: ['#ffffff', '#e0e0e0'] }]
  };

  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: { legend: { display: true } }
  };

  public chartType: ChartType = 'bar';
  public pieChartType: 'pie' = 'pie';

  constructor(private machinesService: MachinesService) {}

  ngOnInit(): void {
    this.machinesService.getAllMachine();
    this.machinesService.machines$.subscribe(machines => {
      this.quantity = machines.length;

      let color = '#ffffff';
      if (this.quantity >= 1 && this.quantity <= 4) color = 'rgba(0, 255, 0, 0.5)';
      if (this.quantity >= 5) color = 'rgba(255, 0, 0, 0.5)';

      this.pieChartData.datasets[0].data = [Math.min(this.quantity, 5), 5 - Math.min(this.quantity, 5)];
      this.pieChartData.datasets[0].backgroundColor = [color, '#e0e0e0'];
    });
  }
}
