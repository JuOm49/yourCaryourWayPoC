import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Message } from '../../pages/interfaces/message.interface';
import { environment } from '../../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class SseChatService {
  private eventSource: EventSource | null = null;
  private messagesSubject = new BehaviorSubject<Message | null>(null);
  private isConnected = false;

  constructor() {}

  /**
   * Connexion au stream SSE pour un ticket spécifique
   * @param ticketId ID du ticket pour lequel écouter les messages
   */
  connectToTicket(ticketId: number): void {
    // Fermer la connexion précédente si elle existe
    this.disconnect();

    const sseUrl = `${environment.apiUrl}/chat/stream/${ticketId}`;

    try {
      this.eventSource = new EventSource(sseUrl);
      
      this.eventSource.onopen = () => {
        this.isConnected = true;
      };

      this.eventSource.onmessage = (event) => {
        try {
          const message: Message = JSON.parse(event.data);
          this.messagesSubject.next(message);
        } catch (error) {
          console.error('Erreur parsing message SSE:', error);
        }
      };

      this.eventSource.onerror = () => {
        this.isConnected = false;
        
        // Reconnexion automatique après 3 secondes
        setTimeout(() => {
          if (this.eventSource?.readyState === EventSource.CLOSED) {
            this.connectToTicket(ticketId);
          }
        }, 3000);
      };
      
    } catch (error) {
      console.error('Erreur lors de la création de EventSource:', error);
    }
  }

  /**
   * Observable pour écouter les nouveaux messages
   */
  onNewMessage(): Observable<Message | null> {
    return this.messagesSubject.asObservable();
  }

  /**
   * Vérifier si la connexion SSE est active
   */
  isConnectedToSSE(): boolean {
    return this.isConnected && this.eventSource?.readyState === EventSource.OPEN;
  }

  /**
   * Fermer la connexion SSE
   */
  disconnect(): void {
    if (this.eventSource) {
      this.eventSource.close();
      this.eventSource = null;
      this.isConnected = false;
    }
  }

}