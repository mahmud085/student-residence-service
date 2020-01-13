import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../shared/models/appointment.model';
import { ConfigService } from './config.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PaginatedAppointments } from '../shared/models/paginated-appointments.model';
import { NewAppointment } from '../shared/models/new-appointment.model';
import { AuthService } from './auth.service';
import { UserRole } from '../shared/enums/user-role.enum';
import { UpdateAppointment } from '../shared/models/update-appointment.model';
import { AppointmentUpdateOperation } from '../shared/enums/appointment-update-operation.enum';

@Injectable({
	providedIn: 'root'
})
export class AppointmentService {

	constructor(private _authService: AuthService,
    private _httpClient: HttpClient) { }
    
  getAppointments(): Observable<PaginatedAppointments> {
    let requestUrl: string = null;
    let userId: string = null;
		switch (this._authService.userCredential.role) {
			case UserRole.Caretaker:
        requestUrl = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`;
        break;
      case UserRole.Admin:
        requestUrl = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`;
        break;
      case UserRole.Resident:
        userId = this._authService.userCredential.userId;
        requestUrl = `${ConfigService.appConfig.service.appointment.baseUrl}/api/contractors/${userId}/appointments`;
        break;
		}
		return this._httpClient.get<PaginatedAppointments>(requestUrl);
  }

  createAppointment(newAppointment: NewAppointment): Observable<Appointment> {
		let requestUrl: string = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`

		return this._httpClient.post<Appointment>(requestUrl, newAppointment);
  }
  
  getFilteredAppointments(filterByDesiredDate: Date): Observable<PaginatedAppointments> {
		let requestUrl: string = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments?desiredDate=${filterByDesiredDate}`;		

		return this._httpClient.get<PaginatedAppointments>(requestUrl);
  }
  
  acceptAppointment(appointmentId: string): Observable<string> {
		let acceptAppointmentRequest: UpdateAppointment = {
			operation: AppointmentUpdateOperation.Accept
		};

		let requestUrl: string = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments/${appointmentId}`;

		return this._httpClient.put<string>(requestUrl, acceptAppointmentRequest, {
			responseType: 'text' as 'json'
		});
  }
  
  cancelAppointment(appointmentId: string): Observable<string> {
		let cancelAppointmentRequest: UpdateAppointment = {
			operation: AppointmentUpdateOperation.Deny
		};

		let requestUrl: string = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments/${appointmentId}`;

		return this._httpClient.put<string>(requestUrl, cancelAppointmentRequest, {
			responseType: 'text' as 'json'
		});
	}
}
