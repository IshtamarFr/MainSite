import { TestBed } from '@angular/core/testing';

import { PasswordService } from './password.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('PasswordService', () => {
  let service: PasswordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(PasswordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
