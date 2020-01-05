import { PaginationMetadata } from './pagination-metadata.model';
import { Appointment } from './appointment.model';

export interface PaginatedAppointments {
	metadata: PaginationMetadata;
	appointment: Appointment[];
}