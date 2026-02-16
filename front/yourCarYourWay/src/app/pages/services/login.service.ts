import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "../../core/interfaces/user.interface";
import { environment } from "../../../environments/environments";
import { catchError } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    constructor(private http: HttpClient) {}

    authentication(email: string, password: string) {
        return this.http.post<User>(`${environment.apiUrl}/login`, {email, password}).pipe(

                    catchError((error) => {
                        console.error('Error fetching login:', error);
                        throw error;
                    })
                );
    }
}