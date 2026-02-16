export interface Ticket {
    id: number;
    clientId: number;
    operatorId: number;
    reservationId: number;
    subject: string;
    description: string;
    status: string;
    createdAt: string;
    updatedAt: string;
    resolvedAt: string;
}