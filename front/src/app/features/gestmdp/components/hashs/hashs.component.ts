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
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Hashs } from '../../interfaces/hashs.interface';

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
    MatSnackBarModule,
  ],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent implements OnInit {
  public randomPassword: string = '';
  public hashs?: Hashs;

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

  public form2 = this.fb.group({
    word: ['', [Validators.required, Validators.max(128)]],
  });

  constructor(
    private fb: FormBuilder,
    private hashService: HashService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.generatePassword();
  }

  onFileDropped(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      const fileReader = new FileReader();

      fileReader.onload = (event: any) => {
        this.hashs = this.hashService.calculateJS(event);
        this.form2.controls['word'].setValue('');
      };
      fileReader.readAsText(file, 'utf-8');
    } else {
      this.hashs = undefined;
    }
  }

  generatePassword(): void {
    this.randomPassword = this.hashService.generatePassword(this.form);
  }

  async copy(): Promise<void> {
    const el = document.getElementById('inputPassword') as HTMLInputElement;
    await navigator.clipboard.writeText(el.value);
    this._snackBar.open('Mot de passe copié dans le presse-papiers', 'fermer', {
      duration: 2500,
    });
  }

  calculateHashs(): void {
    const el = document.getElementById('uploadPicture') as HTMLInputElement;
    el.value = '';
    this.hashs = this.hashService.hashs(this.form2.controls['word']!.value!);
  }
}
