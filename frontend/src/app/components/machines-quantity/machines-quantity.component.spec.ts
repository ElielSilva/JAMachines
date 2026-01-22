import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachinesQuantityComponent } from './machines-quantity.component';

describe('MachinesQuantityComponent', () => {
  let component: MachinesQuantityComponent;
  let fixture: ComponentFixture<MachinesQuantityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MachinesQuantityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MachinesQuantityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
