import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetallComponent } from './getall.component';

describe('GetallComponent', () => {
  let component: GetallComponent;
  let fixture: ComponentFixture<GetallComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GetallComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
