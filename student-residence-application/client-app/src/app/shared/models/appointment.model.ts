import { AppointmentStatus } from '../enums/appointment-status.enum';
import { AppointmentBase } from './appointment-base.model';

export interface Appointment extends AppointmentBase {
	appointmentId: string;
	status: AppointmentStatus;
	createdOn: string;
}