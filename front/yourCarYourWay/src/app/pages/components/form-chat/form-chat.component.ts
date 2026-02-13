import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from '../../services/message.service';
import { SessionService } from '../../../shared/services/session.service';
import { SseChatService } from '../../../shared/services/sse-chat.service';
import { Message, CreateMessage } from '../../interfaces/message.interface';
import { Observable, take, Subscription } from 'rxjs';

@Component({
  selector: 'app-form-chat',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-chat.component.html',
  styleUrl: './form-chat.component.scss'
})
export class FormChatComponent implements OnInit, OnDestroy {

  ticketId: number | null = null;
  messages: Message[] = [];
  newMessage: string = '';
  isLoading: boolean = false;
  isSending: boolean = false;
  private sseSubscription: Subscription | null = null;

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    public sessionService: SessionService,
    private sseChatService: SseChatService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.ticketId = +params['ticketId'];
      this.loadMessages();
      this.connectToSSE();
    });
  }

  ngOnDestroy(): void {
    // Nettoyer les connexions lors de la destruction du composant
    if (this.sseSubscription) {
      this.sseSubscription.unsubscribe();
    }
    this.sseChatService.disconnect();
  }

  /**
   * Connexion au stream SSE pour recevoir les messages en temps réel
   */
  private connectToSSE(): void {
    if (this.ticketId) {
      // Se connecter au stream SSE pour ce ticket
      this.sseChatService.connectToTicket(this.ticketId);
      
      // S'abonner aux nouveaux messages
      this.sseSubscription = this.sseChatService.onNewMessage().subscribe({
        next: (newMessage) => {
          if (newMessage && newMessage.ticketId === this.ticketId) {
            // Vérifier si le message n'est pas déjà dans la liste (éviter les doublons)
            const messageExists = this.messages.find(msg => msg.id === newMessage.id);
            
            if (!messageExists) {
              this.messages.push(newMessage);
              this.messages.sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime());
              
              // Forcer Angular à détecter les changements pour mettre à jour la vue
              this.cdr.detectChanges();
            }
          }
        },
        error: (error) => {
          console.error('Erreur dans l\'observable SSE:', error);
        }
      });
    }
  }

  loadMessages(): void {
    if (this.ticketId && this.sessionService.user) {
      this.isLoading = true;

      this.messageService.getMessagesByUserId(this.sessionService.user.id, this.ticketId)
      .pipe(take(1))
        .subscribe({
          next: (messages) => {
            this.messages = messages.sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime());
            this.isLoading = false;
          },
          error: (error) => {
            console.error('Erreur lors du chargement des messages:', error);
            this.isLoading = false;
          }
        });
    }
  }

  sendMessage(): void {
    if (this.newMessage.trim() && this.ticketId && this.sessionService.user) {
      this.isSending = true;
      
      const newMsg: CreateMessage = {
        ticketId: this.ticketId!,
        clientId:  this.sessionService.user?.role === null ? this.sessionService.user!.id : 0,
        operatorId: this.sessionService.user?.role !== null ? this.sessionService.user!.id : 0,
        reservationId: 2,
        title: 'Chat PoC',
        messageText: this.newMessage,
        sentAt: new Date().toISOString(),
        readAt: '',
        attachment: ''
      };

      this.messageService.createMessage(newMsg)
      .pipe(take(1))
        .subscribe({
          next: () => {
            // Le nouveau message arrivera automatiquement via SSE
            this.newMessage = '';
            this.isSending = false;
          },
          error: (error) => {
            console.error('Erreur lors de l\'envoi du message:', error);
            this.isSending = false;
          }
        });
    }
  }

  isMyMessage(message: Message): boolean {
    if (!this.sessionService.user) {
      return false;
    }
  
    // Client
    if (this.sessionService.user.role === null) {
      return message.clientId === this.sessionService.user.id;
    }
  
    // Operator
    if (this.sessionService.user.role !== null) {
      return message.operatorId === this.sessionService.user.id;
    }
  
    return false;
  }

  formatTime(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }

}
