import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HashsComponent } from './hashs.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';

describe('HashsComponent', () => {
  let component: HashsComponent;
  let fixture: ComponentFixture<HashsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HashsComponent,
        RouterModule.forRoot([]),
        NoopAnimationsModule,
        HttpClientTestingModule,
      ],
      providers: [],
    }).compileComponents();

    fixture = TestBed.createComponent(HashsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('sha256 should work', () => {
    expect(component.sha256('test')).toBe(
      '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08'
    );
  });
});
