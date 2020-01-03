import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserRole } from 'src/app/shared/enums/user-role.enum';
import { Appointment } from 'src/app/shared/models/appointment.model';
import { AppointmentStatus } from 'src/app/shared/enums/appointment-status.enum';
import { DropdownItem } from 'src/app/shared/models/dropdown-item.mode';
import { AppointmentType } from 'src/app/shared/enums/appointment-type.enum';

@Component({
	selector: 'app-appointment',
	templateUrl: './appointment.component.html',
	styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {

	get appointmentTypeOptions(): DropdownItem[] {
		let options: DropdownItem[] = [
			{
				value: '',
				label: 'Select a Type'
			}
		];

		Object.keys(AppointmentType).map((k: string): void => {
			options.push({
				value: AppointmentType[k],
				label: k
			});
		});

		return options;
	}

	get appointmentPriorityOptions(): DropdownItem[] {
		let options: DropdownItem[] = [
			{
				value: '',
				label: 'Select a Priority'
			}
		];

		Object.keys(AppointmentType).map((k: string): void => {
			options.push({
				value: AppointmentType[k],
				label: k
			});
		});

		return options;
	}

	constructor(private _authService: AuthService,
		private _appointmentService: AppointmentService) { }

	ngOnInit() {
	}

	showCreateAppointmentModal(): void {
		$('#createAppointmentModal').modal({
			backdrop: 'static',
			keyboard: false
		});
		$("#createAppointmentModal").modal().show();		
	}

	hideCreateAppointmnetModal(): void {
		$('#close-new-appointment-modal').click();
	}

	isUserCareTaker(): boolean {
		return this._authService.userCredential.role == UserRole.Caretaker;
	}

	isUserResident(): boolean {
		return this._authService.userCredential.role == UserRole.Resident;
	}

	isAppointmentAccepted(contract: Appointment):boolean {
		return contract.status == AppointmentStatus.Accepted;
	}
}
