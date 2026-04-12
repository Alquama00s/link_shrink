import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent{
  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  signUpForm = new FormGroup({
    name: new FormControl<string>('', []),
    email: new FormControl<string>('', []),
    password: new FormControl<string>('', []),
    reEnterPassword: new FormControl<string>('', []),
  });

  login() {
    this.router.navigate(['web', 'login']);
  }

  signUp() {
    if (
      this.signUpForm.get('password')?.value != this.signUpForm.get('reEnterPassword')?.value
    ) {
      this.snackBar.open('passwords does not match', undefined, {
        duration: 1500,
        panelClass: ['snack-bar-red'],
      });
      return;
    }
    this.authService
      .signUp(
        this.signUpForm.get('name')?.value??"",
        this.signUpForm.get('email')?.value??"",
        this.signUpForm.get('password')?.value??""
      )
      .subscribe((res) => this.login());
  }
}
