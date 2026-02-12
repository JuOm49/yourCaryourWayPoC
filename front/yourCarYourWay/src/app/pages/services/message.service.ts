import { Injectable } from "@angular/core";

import { HttpClient } from "@angular/common/http";

import { Message } from "../interfaces/message.interface";

import { environment } from "../../../environments/environments";
import { catchError, Observable } from "rxjs";
import { Ticket } from "../interfaces/ticket.interface";

@Injectable({
    providedIn: 'root'
})
export class MessageService {
    constructor(private http: HttpClient) {}

    getTickets(): Observable<Ticket[]> {
        return this.http.get<Ticket[]>(`${environment.apiUrl}/tickets`).pipe(
            catchError((error) => {
                console.error('Error fetching tickets:', error);
                throw error;
            })
        );
    }
    
    getMessagesByClientId(clientId: number, ticketId: number): Observable<Message[]> {
        return this.http.get<Message[]>(`${environment.apiUrl}/messages/${clientId}/${ticketId}`).pipe(
            catchError((error) => {
                console.error('Error fetching messages:', error);
                throw error;
            })
        );
    }
    
    createMessage(message: string, ticketId: number, userId: number): Observable<void> {
        return this.http.post<void>(`${environment.apiUrl}/messages`, { message, ticketId, userId }).pipe(
            catchError((error) => {
                console.error('Error creating message:', error);
                throw error;
            })
        );
    }
}