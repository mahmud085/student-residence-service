import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../shared/models/appointment.model';
import { ConfigService } from './config.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PaginatedAppointments } from '../shared/models/paginated-appointments.model';
import { NewAppointment } from '../shared/models/new-appointment.model';
import { AuthService } from './auth.service';
import { UserRole } from '../shared/enums/user-role.enum';

@Injectable({
	providedIn: 'root'
})
export class AppointmentService {

	constructor(private _authService: AuthService,
    private _httpClient: HttpClient) { }
    
  getAppointments(): Observable<PaginatedAppointments> {
		let requestUrl: string = null;
		console.log('AppointmentService ',this._authService.userCredential.role)
		switch (this._authService.userCredential.role) {
			case UserRole.Caretaker || UserRole.Administrator:
        requestUrl = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`;
        break;
        case UserRole.Resident:
          requestUrl = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`;
		}
    console.log('AppointmentService ',requestUrl)
		return this._httpClient.get<PaginatedAppointments>(requestUrl);
  }

  createAppointment(newAppointment: NewAppointment): Observable<Appointment> {
		let requestUrl: string = `${ConfigService.appConfig.service.appointment.baseUrl}/api/appointments`

		return this._httpClient.post<Appointment>(requestUrl, newAppointment);
	}
}
