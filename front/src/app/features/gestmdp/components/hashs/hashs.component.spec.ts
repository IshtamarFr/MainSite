import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HashsComponent } from './hashs.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import CryptoJS from 'crypto-js';

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
    expect(CryptoJS.SHA256('test').toString()).toBe(
      '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08'
    );
  });

  it('sha1 should work', () => {
    expect(CryptoJS.SHA1('test').toString()).toBe(
      'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'
    );
  });

  it('md5 should work', () => {
    expect(CryptoJS.MD5('test').toString()).toBe(
      '098f6bcd4621d373cade4e832627b4f6'
    );
  });

  it('sha512 should work', () => {
    expect(CryptoJS.SHA512('test').toString()).toBe(
      'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff'
    );
  });
});
