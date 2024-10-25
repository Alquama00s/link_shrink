import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  email = '';
  password = '';
  reEnterPassword = '';
  name = '';

  login() {
    this.router.navigate(['web', 'login']);
  }

  signUp() {
    if (this.password != this.reEnterPassword) {
      this.snackBar.open('passwords does not match', undefined, {
        duration: 1500,
        panelClass: ['snack-bar-red'],
      });
      return;
    }
    this.authService
      .signUp(this.name, this.email, this.password)
      .subscribe((res) => this.login());
  }
}
