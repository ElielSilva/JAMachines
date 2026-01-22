import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule, HttpClientModule, CommonModule, FormsModule
  ],

  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';

  constructor(private router: Router, private auth: AuthService) {}

  login() {
    this.auth.login(this.email, this.password).subscribe({
      next: (res) => {
        console.log("ok")
        this.auth.setToken(res.token);
        console.log("ok 1")
        this.router.navigate(['/dashboard']);
        console.log("ok 2")

      },
      error: () => alert('Email ou senha incorretos!')
    });
  }
}
