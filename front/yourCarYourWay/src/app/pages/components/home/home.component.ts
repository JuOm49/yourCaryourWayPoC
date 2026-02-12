import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageService } from '../../services/message.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { Observable } from 'rxjs';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  tickets$!: Observable<Ticket[]>;

  constructor(private messageService: MessageService, private ticketService: TicketService, private router: Router) { }


  ngOnInit() {
   this.tickets$ = this.ticketService.getTickets();
  }

  openChat(ticketId: number) {
    this.router.navigate(['/chat', ticketId]);
  }
}
