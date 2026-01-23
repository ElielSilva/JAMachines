import { Component } from '@angular/core';
import { HeaderComponent } from '../../layout/header/header.component';
import { MachinesComponent } from '../../components/machines-list/machines-list.component';
import { MachinesStatusComponent } from '../../components/machines-status/machines-status.component';
import { MachinesQuantityComponent } from '../../components/machines-quantity/machines-quantity.component';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    HeaderComponent,
    MachinesComponent,
    MachinesStatusComponent,
    MachinesQuantityComponent,
    RouterOutlet
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {}
