import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdministradorCrearComponent } from './administrador-crear.component';

describe('AdministradorComponentComponent', () => {
  let component: AdministradorCrearComponent;
  let fixture: ComponentFixture<AdministradorCrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministradorCrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministradorCrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
