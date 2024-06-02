import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-hashs',
  standalone: true,
  imports: [CommonModule, RouterModule, MatInputModule],
  templateUrl: './hashs.component.html',
  styleUrl: './hashs.component.scss',
})
export class HashsComponent {
  sha256(message: string): string {
    return CryptoJS.SHA256(message).toString();
  }
}
