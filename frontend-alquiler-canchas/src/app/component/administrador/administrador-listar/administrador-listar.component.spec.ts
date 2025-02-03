import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministradorListarComponent } from './administrador-listar.component';

describe('AdministradorListarComponent', () => {
  let component: AdministradorListarComponent;
  let fixture: ComponentFixture<AdministradorListarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministradorListarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministradorListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
