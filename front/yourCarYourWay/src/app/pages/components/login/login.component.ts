import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { Observable, take } from 'rxjs';
import { User } from '../../../core/interfaces/user.interface';
import { CommonModule } from '@angular/common';
import { SessionService } from '../../../shared/services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  user$!:  Observable<User>;

  constructor(private loginService: LoginService, private sessionService: SessionService, private router: Router) {}

  readonly labelsForInterface = {
    login: 'Se connecter',
    email: 'E-mail',
    password: 'Mot de passe',
    submit: 'Valider'
  }

  onSubmit(): void {
    console.log('Email:', this.email);
    console.log('Password:', this.password);
    this.loginService.authentication(this.email, this.password).pipe(take(1)).subscribe({
      next: (response: User) => {
        this.sessionService.login(response);
        this.router.navigate(['/home']);
      },
      error: (error) => { 
        console.error('Error fetching login:', error);
      }
    });
  }

}
