import { Injectable } from "@angular/core";

import { HttpClient } from "@angular/common/http";

import { Message, CreateMessage } from "../interfaces/message.interface";

import { environment } from "../../../environments/environments";
import { catchError, Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class MessageService {
    constructor(private http: HttpClient) {}
    
    getMessagesByUserId(userId: number, ticketId: number): Observable<Message[]> {
        return this.http.get<Message[]>(`${environment.apiUrl}/messages/${userId}/${ticketId}`).pipe(
            catchError((error) => {
                console.error('Error fetching messages:', error);
                throw error;
            })
        );
    }
    
    createMessage(newMessage: CreateMessage): Observable<void> {
        return this.http.post<void>(`${environment.apiUrl}/messages/create`, newMessage).pipe(
            catchError((error) => {
                console.error('Error creating message:', error);
                throw error;
            })
        );
    }
}