import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface Machine {
  id: string,
  name: string,
  cpu: number,
  memory: number,
  disk: number,
  machineStatus: string,
  createdAt: string
}

@Injectable({
  providedIn: 'root'
})
export class MachinesService {
  private api = 'http://localhost:8080/machine';
  private machinesSubject = new BehaviorSubject<Machine[]>([]);
  public machines$ = this.machinesSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAllMachine(): void {
    this.http.get<Machine[]>(`${this.api}`).subscribe({
      next: machines => this.machinesSubject.next(machines),
      error: err => console.error('Erro ao buscar máquinas', err)
    });
  }

  getMachineById(id: number): Observable<Machine> {
    return this.http.get<Machine>(`${this.api}/${id}`);
  }

  updateMachine(machine: Machine): Observable<Machine> {
    return this.http.put<Machine>(`${this.api}/${machine.id}`, machine)
      .pipe(
        tap(updated => {
          // Atualiza localmente o BehaviorSubject
          const current = this.machinesSubject.value;
          const index = current.findIndex(m => m.id === updated.id);
          if (index !== -1) {
            current[index] = updated;
            this.machinesSubject.next([...current]);
          }
        })
      );
  }

  patchStatus(machineId: string, newStatus: string): Observable<Machine> {
    console.log("patch =>", machineId, newStatus);
    const body = { status: newStatus };

    return this.http.patch<Machine>(`${this.api}/${machineId}/status`, body).pipe(
      tap(updatedMachine => {
        const machines = this.machinesSubject.value;
        const index = machines.findIndex(m => m.id === machineId);
        if (index !== -1) {
          machines[index] = updatedMachine;
          this.machinesSubject.next([...machines]);
        }
      })
    );
}

  deleteMachine(id: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`)
      .pipe(
        tap(() => {
          const current = this.machinesSubject.value.filter(m => m.id !== id);
          this.machinesSubject.next(current);
        })
      );
  }

  createMachine(machine: Omit<Machine, 'id'>): Observable<Machine> {
    return this.http.post<Machine>(this.api, machine)
      .pipe(
        tap(created => {
          this.machinesSubject.next([...this.machinesSubject.value, created]);
        })
      );
  }
}