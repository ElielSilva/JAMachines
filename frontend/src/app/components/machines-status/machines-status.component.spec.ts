import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachinesStatusComponent } from './machines-status.component';

describe('MachinesStatusComponent', () => {
  let component: MachinesStatusComponent;
  let fixture: ComponentFixture<MachinesStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MachinesStatusComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MachinesStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
