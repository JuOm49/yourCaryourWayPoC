import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', loadComponent: () => import('./pages/components/login/login.component').then(m => m.LoginComponent) },
    { path: 'home', loadComponent: () => import('./pages/components/home/home.component').then(m => m.HomeComponent) }, 
    { path: 'chat/:ticketId', loadComponent: () => import ('./pages/components/form-chat/form-chat.component').then(m => m.FormChatComponent) },
    { path: '**', redirectTo: '' }
];
