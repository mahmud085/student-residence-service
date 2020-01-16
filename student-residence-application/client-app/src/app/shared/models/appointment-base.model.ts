import { AppointmentType } from '../enums/appointment-type.enum';
import { AppointmentPriority } from '../enums/appointment-priority.enum';

export interface AppointmentBase {
	contractorsName: string;
	contractId: string;
	roomNumber: string;
	issue: string;
	appointmentType: AppointmentType;
	priority: AppointmentPriority;
	desiredDate: string;
}