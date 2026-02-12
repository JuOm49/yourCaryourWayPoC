export interface Ticket {
    id: number;
    client_id: number;
    operator_id: number;
    reservation_id: number;
    subject: string;
    description: string;
    status: string;
    created_at: string;
    updated_at: string;
    resolved_at: string;
}