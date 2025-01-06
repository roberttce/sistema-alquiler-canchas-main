import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservausuarioComponent } from './reservausuario.component';

describe('ReservausuarioComponent', () => {
  let component: ReservausuarioComponent;
  let fixture: ComponentFixture<ReservausuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservausuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservausuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
