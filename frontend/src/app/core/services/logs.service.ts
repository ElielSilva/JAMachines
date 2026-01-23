import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MachineStatusLogResponseDTO {
  id: string;
  userName: string;
  machineName: string;
  dateTime: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class LogsService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/machine/log';

  getAll(): Observable<MachineStatusLogResponseDTO[]> {
    return this.http.get<MachineStatusLogResponseDTO[]>(this.API);
  }
}