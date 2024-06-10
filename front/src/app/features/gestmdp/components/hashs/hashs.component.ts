import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { MatCheckboxModule } from '@angular/material/checkbox';
import CryptoJS from 'crypto-js';

@Component({
  selector: 'app-hashs',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
  ],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent {
  public fileHash: string = '';

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
  });

  constructor(private fb: FormBuilder) {}

  onFileDropped(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      const fileReader = new FileReader();

      fileReader.onload = (event: any) => {
        if (event.target && event.target.result) {
          const content = event.target.result.toString();
          const normalizedContent = this.normalizeContent(content);

          const md5 = CryptoJS.MD5(normalizedContent).toString(
            CryptoJS.enc.Base64
          );
          const sha1 = CryptoJS.SHA1(normalizedContent).toString(
            CryptoJS.enc.Base64
          );
          const sha256 = CryptoJS.SHA256(normalizedContent).toString(
            CryptoJS.enc.Base64
          );
          const sha512 = CryptoJS.SHA512(normalizedContent).toString(
            CryptoJS.enc.Base64
          );

          this.fileHash = `Hash MD5: ${md5}\nHash SHA-1: ${sha1}\nHash SHA-256: ${sha256}\nHash SHA-512: ${sha512}`;
          console.log(this.fileHash);
        }
      };

      fileReader.readAsText(file, 'utf-8');
    }
  }

  private normalizeContent(content: string): string {
    content = content.replace(/&/g, '&amp;');
    content = content.replace(/</g, '&lt;');
    content = content.replace(/>/g, '&gt;');

    content = content.replace(/\r\n/g, '\n');
    content = content.replace(/\n/g, '\r\n');

    return content;
  }

  generatePassword(): string {
    return 'WIP';
  }
}
