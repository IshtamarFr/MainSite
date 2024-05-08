import { Component, NgZone, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { SessionService } from '../../services/session.service';
import { CommonModule, NgIf, NgTemplateOutlet } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { User } from '../../interfaces/user.interface';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatIconModule,
    MatSidenavModule,
    MatDividerModule,
    NgTemplateOutlet,
    MatTooltipModule,
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent implements OnInit {
  constructor(
    private router: Router,
    private sessionService: SessionService,
    private ngZone: NgZone
  ) {}

  isLogged: boolean = false;
  user?: User;

  ngOnInit(): void {
    this.sessionService.$isLogged().subscribe((resp) => (this.isLogged = resp));
    this.user = this.sessionService.user;
  }

  public logout(): void {
    this.sessionService.logOut();
    this.ngZone.run(() => {
      this.router.navigate(['']);
    });
  }
}
