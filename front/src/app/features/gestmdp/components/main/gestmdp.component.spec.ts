import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { GestmdpComponent } from './gestmdp.component';

describe('GestmdpComponent', () => {
  let component: GestmdpComponent;
  let fixture: ComponentFixture<GestmdpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        GestmdpComponent,
        RouterModule.forRoot([]),
        NoopAnimationsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(GestmdpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
