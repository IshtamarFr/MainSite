import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { MatCheckboxModule } from '@angular/material/checkbox';
import CryptoJS from 'crypto-js';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HashService } from '../../services/hash.service';

@Component({
  selector: 'app-hashs',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
  ],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent implements OnInit {
  public randomPassword: string = '';

  LOWERCASES: string = 'abcdefghijklmnopqrstuvwxyz';
  UPPERCASES: string = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
  DIGITS: string = '0123456789';
  SYMBOLS: string = "!@#$%^&*()-+={}[]|:;',<.>/?_";

  ALL_CHARS = this.DIGITS + this.LOWERCASES + this.UPPERCASES + this.SYMBOLS;

  passwordLength: number = 64;

  public form = this.fb.group({
    lowercases: [true],
    uppercases: [true],
    digits: [true],
    symbols: [true],
    length: [
      64,
      [
        Validators.required,
        Validators.min(8),
        Validators.max(128),
        Validators.pattern('^[0-9]*$'),
      ],
    ],
  });

  constructor(private fb: FormBuilder, private hashService: HashService) {}

  ngOnInit(): void {
    this.generatePassword();
  }

  onFileDropped(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      const fileReader = new FileReader();

      fileReader.onload = this.hashService.calculateJS;
      fileReader.readAsText(file, 'utf-8');
    }
  }

  generatePassword(): void {
    console.log(this.form.controls['lowercases'].value);
  }
}
