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
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HashService } from '../../services/hash.service';
import { DragSingleDirective } from '../../../../directives/drag-single.directive';
import { MatFormFieldModule } from '@angular/material/form-field';

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
    MatFormFieldModule,
    MatCheckboxModule,
    DragSingleDirective,
  ],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent implements OnInit {
  public randomPassword: string = '';

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

      fileReader.onload = (event: any) => this.hashService.calculateJS(event);
      fileReader.readAsText(file, 'utf-8');
    }
  }

  generatePassword(): void {
    this.randomPassword = this.hashService.generatePassword(this.form);
    console.log(this.randomPassword);
  }
}
