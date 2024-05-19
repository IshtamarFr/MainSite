import { RouterModule } from '@angular/router';
import { GestmdpMenuComponent } from './gestmdp-menu.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CategoryService } from '../../services/category.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('GestmdpMenuComponent', () => {
  let component: GestmdpMenuComponent;
  let fixture: ComponentFixture<GestmdpMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        GestmdpMenuComponent,
        RouterModule.forRoot([]),
        NoopAnimationsModule,
        HttpClientTestingModule,
      ],
      providers: [CategoryService],
    }).compileComponents();

    fixture = TestBed.createComponent(GestmdpMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
