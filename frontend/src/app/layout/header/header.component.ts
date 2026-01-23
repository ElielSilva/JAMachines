import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  userName: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    const token = sessionStorage.getItem('authToken') || "";
    const payload = JSON.parse(atob(token.split('.')[1]));
    console.log("token ",token)
    if (payload) {
      this.userName = payload.name || 'Usuário';
    }
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}