import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageService } from '../../services/message.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { Observable } from 'rxjs';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { SessionService } from '../../../shared/services/session.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  tickets$!: Observable<Ticket[]>;

  constructor(
    private ticketService: TicketService, 
    private router: Router,
    public sessionService: SessionService
  ) { }


  ngOnInit() {
    if( this.sessionService.isLogged ) {
      if(this.sessionService.user?.role !== null) {
        this.tickets$ = this.ticketService.getTickets();
      }
      else {
        this.tickets$ = this.ticketService.getTicketsByClientId(this.sessionService.user!.id);
      }
    }
  }

  openChat(ticketId: number) {
    this.router.navigate(['/chat', ticketId]).then(success => {
      console.log('Navigation réussie:', success);
    }).catch(error => {
      console.error('Erreur de navigation:', error);
    });
  }

  getUserDisplayName(): string {
    if (this.sessionService.user) {
      const firstName = this.sessionService.user.firstName;
      const lastName = this.sessionService.user.lastName;

      if(this.sessionService.user.role !== null) {
        return `${firstName} ${lastName} (${this.sessionService.user.role})`;
      }
      
      if (firstName && lastName) {
        return `${firstName} ${lastName}`;
      }
    }
    return 'Utilisateur';
  }
}
