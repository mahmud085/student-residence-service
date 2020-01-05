import { AppointmentUpdateOperation } from '../enums/appointment-update-operation.enum';

export interface UpdateAppointment {
	operation: AppointmentUpdateOperation;
}