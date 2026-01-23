import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachinesLogsComponent } from './machines-logs.component';

describe('MachinesLogsComponent', () => {
  let component: MachinesLogsComponent;
  let fixture: ComponentFixture<MachinesLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MachinesLogsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MachinesLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
