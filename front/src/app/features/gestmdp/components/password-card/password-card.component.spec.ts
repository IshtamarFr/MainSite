import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { PasswordCardComponent } from './password-card.component';

describe('PasswordCardComponent', () => {
  let component: PasswordCardComponent;
  let fixture: ComponentFixture<PasswordCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        PasswordCardComponent,
        RouterModule.forRoot([]),
        NoopAnimationsModule,
        HttpClientTestingModule,
      ],
      providers: [],
    }).compileComponents();

    fixture = TestBed.createComponent(PasswordCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
