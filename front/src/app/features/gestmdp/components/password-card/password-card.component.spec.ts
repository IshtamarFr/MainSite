import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { PasswordCardComponent } from './password-card.component';
import { Password } from '../../interfaces/password.interface';

describe('PasswordCardComponent', () => {
  let component: PasswordCardComponent;
  let fixture: ComponentFixture<PasswordCardComponent>;

  const mockPassword: Password = {
    id: 40,
    siteName: 'Amadron',
    active: true,
    category_id: 2,
    user_id: 1,
  };

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
    component.password = mockPassword;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
