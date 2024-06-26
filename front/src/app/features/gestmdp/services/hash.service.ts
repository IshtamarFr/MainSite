import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import CryptoJS from 'crypto-js';
import { Hashs } from '../interfaces/hashs.interface';

@Injectable({
  providedIn: 'root',
})
export class HashService {
  LOWERCASES: string = 'abcdefghijklmnopqrstuvwxyz';
  UPPERCASES: string = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
  DIGITS: string = '0123456789';
  SYMBOLS: string = "!@#$%^&*()-+={}[]|:;',<.>/?_";

  ALL_CHARS = this.DIGITS + this.LOWERCASES + this.UPPERCASES + this.SYMBOLS;

  constructor() {}

  public calculateJS(event: any): Hashs {
    if (event.target && event.target.result) {
      const content = event.target.result.toString();
      const normalizedContent = this.normalizeContent(content);
      return this.hashs(normalizedContent);
    } else {
      return { md5: '', sha1: '', sha256: '', sha512: '' };
    }
  }

  public hashs(content: string): Hashs {
    const md5 = CryptoJS.MD5(content).toString();
    const sha1 = CryptoJS.SHA1(content).toString();
    const sha256 = CryptoJS.SHA256(content).toString();
    const sha512 = CryptoJS.SHA512(content).toString();
    return { md5, sha1, sha256, sha512 };
  }

  private normalizeContent(content: string): string {
    content = content.replace(/&/g, '&amp;');
    content = content.replace(/</g, '&lt;');
    content = content.replace(/>/g, '&gt;');

    content = content.replace(/\r\n/g, '\n');
    content = content.replace(/\n/g, '\r\n');

    return content;
  }

  public generatePassword(form: FormGroup): string {
    var pwd: string = '';
    var length: number = form.controls['length'].value;

    if (form.controls['lowercases'].value) {
      pwd += this.randomAt(this.LOWERCASES);
      length--;
    }
    if (form.controls['uppercases'].value) {
      pwd += this.randomAt(this.UPPERCASES);
      length--;
    }
    if (form.controls['digits'].value) {
      pwd += this.randomAt(this.DIGITS);
      length--;
    }
    if (form.controls['symbols'].value) {
      pwd += this.randomAt(this.SYMBOLS);
      length--;
    }

    for (let comp = 0; comp < length; comp++) {
      pwd += this.randomAt(this.ALL_CHARS);
    }

    return this.randomizeString(pwd);
  }

  private randomAt(word: string): string {
    return word.charAt(Math.floor(Math.random() * word.length));
  }

  private randomizeString(str: string): string {
    const chars = str.split('');

    for (let i = chars.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [chars[i], chars[j]] = [chars[j], chars[i]];
    }

    return chars.join('');
  }
}
