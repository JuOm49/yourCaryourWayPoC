export interface Message {
    id?: number;
    reservationId: number;
    clientId?: number;
    operatorId?: number;
    ticketId: number;
    title?: string;
    messageText: string;
    sentAt: string;
    readAt?: string;
    attachment?: string;
}
