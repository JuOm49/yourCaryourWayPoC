export interface Message {
    id: number;
    reservation_id: number;
    client_id: number;
    operator_id: number;
    ticket_id: number;
    title: string;
    messsage_text: string;
    sent_at: string;
    read_at: string;
    attachment: string;
}