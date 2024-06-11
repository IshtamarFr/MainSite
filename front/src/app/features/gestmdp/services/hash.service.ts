import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root',
})
export class HashService {
  constructor() {}

  public calculateJS(event: any): {
    md5: string;
    sha1: string;
    sha256: string;
    sha512: string;
  } {
    if (event.target && event.target.result) {
      const content = event.target.result.toString();
      const normalizedContent = this.normalizeContent(content);

      const md5 = CryptoJS.MD5(normalizedContent).toString(CryptoJS.enc.Base64);
      const sha1 = CryptoJS.SHA1(normalizedContent).toString(
        CryptoJS.enc.Base64
      );
      const sha256 = CryptoJS.SHA256(normalizedContent).toString(
        CryptoJS.enc.Base64
      );
      const sha512 = CryptoJS.SHA512(normalizedContent).toString(
        CryptoJS.enc.Base64
      );
      return { md5, sha1, sha256, sha512 };
    } else {
      return { md5: '', sha1: '', sha256: '', sha512: '' };
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
}
