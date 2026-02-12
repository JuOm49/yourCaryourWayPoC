import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environments";
import { catchError, Observable } from "rxjs";
import { Ticket } from "../interfaces/ticket.interface";

@Injectable({
    providedIn: 'root'
})
export class TicketService {

    constructor(private http: HttpClient) {}

    getTickets(): Observable<Ticket[]> {
        return this.http.get<Ticket[]>(`${environment.apiUrl}/tickets`).pipe(
            catchError((error) => {
                console.error('Error fetching tickets:', error);
                throw error;
            })
        );
    }

}