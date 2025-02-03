import { TestBed } from '@angular/core/testing';

import { CanchadeporteService } from './canchadeporte.service';

describe('CanchadeporteService', () => {
  let service: CanchadeporteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CanchadeporteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
