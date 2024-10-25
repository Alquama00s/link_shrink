import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  constructor(private router: Router, private auth: AuthService) {}

  profile: any = {};

  ngOnInit(): void {
    this.auth.profile().subscribe((res) => this.parseProfile(res));
  }

  private parseProfile(profileResp: any) {
    var role: any[] = [];
    var auth: any[] = [];
    profileResp.authorities.forEach((element: string) => {
      if (element.toString().startsWith('ROLE_')) {
        role.push(element.substring(5));
      } else {
        auth.push(element);
      }
    });
    this.profile = {
      photoUrl: 'https://picsum.photos/200', // Replace with actual photo URL
      name: profileResp.name,
      email: profileResp.email,
      roles: role,
      authorities: auth,
    };
  }

  goBack() {
    this.router.navigate(['web']);
  }

  logout() {
    this.auth.logOut();
  }
}
