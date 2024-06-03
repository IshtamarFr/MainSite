import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import CryptoJS from 'crypto-js';

@Component({
  selector: 'app-hashs',
  standalone: true,
  imports: [CommonModule, RouterModule, MatInputModule],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent {
  public fileChange(event: any): void {
    if (event.target.files.length > 0) {
      var file = event.target.files[0];
      var fileReader = new FileReader();

      fileReader.onload = (_) => {
        const content = fileReader.result!.toString();
        const md5 = CryptoJS.MD5(content).toString();
        console.log(md5);
      };

      fileReader.readAsText(file);
    }
  }
}
