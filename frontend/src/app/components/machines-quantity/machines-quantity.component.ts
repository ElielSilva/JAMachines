import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachinesService } from '../../core/services/machines.service';
import { ChartData, ChartOptions, ChartType, ChartTypeRegistry } from 'chart.js';
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js';

import { BaseChartDirective } from 'ng2-charts';
import { takeUntil } from 'rxjs/internal/operators/takeUntil';
import { Subject } from 'rxjs/internal/Subject';


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
  private destroy$ = new Subject<void>();
  
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

    this.machinesService.machines$
      .pipe(takeUntil(this.destroy$))
      .subscribe(machines => {
        this.updateChart(machines.length);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private updateChart(quantity: number) {
    this.quantity = quantity;

    let color = '#ffffff';
    if (quantity >= 1 && quantity <= 4) color = 'rgba(0, 255, 0, 0.5)';
    if (quantity >= 5) color = 'rgba(255, 0, 0, 0, 0.5)';

    const value = Math.min(quantity, 5);

    this.pieChartData = {
      ...this.pieChartData,
      datasets: [
        {
          ...this.pieChartData.datasets[0],
          data: [value, 5 - value],
          backgroundColor: [color, '#e0e0e0']
        }
      ]
    };
  }
}
