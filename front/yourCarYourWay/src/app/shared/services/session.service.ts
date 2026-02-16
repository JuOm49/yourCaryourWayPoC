import { Injectable } from "@angular/core";

import { BehaviorSubject } from "rxjs";

import { User } from "../../core/interfaces/user.interface";

// Service to manage user session state and authentication status
@Injectable({
    providedIn: "root"
})
export class SessionService {
    private static readonly USER_STORAGE_KEY = 'yourCarYourWay_user';
    private static readonly IS_LOGGED_STORAGE_KEY = 'yourCarYourWay_isLogged';

    public isLogged = false;
    public user: User | undefined;

    private isLoggedBehaviorSubject = new BehaviorSubject<boolean>(this.isLogged);

    constructor() {
        this.loadSessionFromStorage();
        this.nextLogged();
    }

    public login(user: User): void {
        this.user = user;
        this.isLogged = true;
        this.saveSessionToStorage();
        this.nextLogged();
    }

    public logout(): void {
        this.user = undefined;
        this.isLogged = false;
        this.clearSessionFromStorage();
        this.nextLogged();
    }

    public getIsLoggedBehaviorSubjectAsObservable() {
        return this.isLoggedBehaviorSubject.asObservable();
    }
    
    private nextLogged(): void {
        this.isLoggedBehaviorSubject.next(this.isLogged);
    }

    private loadSessionFromStorage(): void {
        try {
            const savedUser = sessionStorage.getItem(SessionService.USER_STORAGE_KEY);
            const savedIsLogged = sessionStorage.getItem(SessionService.IS_LOGGED_STORAGE_KEY);
            
            if (savedUser && savedIsLogged === 'true') {
                this.user = JSON.parse(savedUser);
                this.isLogged = true;
            }
        } catch (error) {
            console.error('Erreur lors du chargement de la session depuis sessionStorage:', error);
            this.clearSessionFromStorage();
        }
    }

    private saveSessionToStorage(): void {
        try {
            if (this.user) {
                sessionStorage.setItem(SessionService.USER_STORAGE_KEY, JSON.stringify(this.user));
                sessionStorage.setItem(SessionService.IS_LOGGED_STORAGE_KEY, 'true');
            }
        } catch (error) {
            console.error('Erreur lors de la sauvegarde de la session dans sessionStorage:', error);
        }
    }

    private clearSessionFromStorage(): void {
        try {
            sessionStorage.removeItem(SessionService.USER_STORAGE_KEY);
            sessionStorage.removeItem(SessionService.IS_LOGGED_STORAGE_KEY);
        } catch (error) {
            console.error('Erreur lors de la suppression de la session du sessionStorage:', error);
        }
    }
}