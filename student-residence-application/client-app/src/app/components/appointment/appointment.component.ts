import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserRole } from 'src/app/shared/enums/user-role.enum';
import { Appointment } from 'src/app/shared/models/appointment.model';
import { AppointmentStatus } from 'src/app/shared/enums/appointment-status.enum';
import { DropdownItem } from 'src/app/shared/models/dropdown-item.mode';
import { AppointmentType } from 'src/app/shared/enums/appointment-type.enum';
import { PaginatedAppointments } from 'src/app/shared/models/paginated-appointments.model';

@Component({
	selector: 'app-appointment',
	templateUrl: './appointment.component.html',
	styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  characters: any[] = [
    {
    actor_name: 'Peter Dinklage',
    character_name: 'Tyrion Lannister',
    gender: 'Male',
        status: 'Alive'
    },
    {
    actor_name: 'Sean Bean',
    character_name: 'Ned Stark',
    gender: 'Male',
    status: 'Dead'
    },
    {
    actor_name: 'Emilia Clark',
    character_name: 'Khaleesi',
    gender: 'Female',
    status: 'Alive'
    },
    {
    actor_name: 'Catelyn Stark',
    character_name: 'Michelle Fairley',
    gender: 'Female',
    status: 'Dead'
    }
  ];
  appointments: Appointment[];
  hello: 'Hi hello';
  blockUI: boolean;
  
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
		private _appointmentService: AppointmentService) { 
      this.blockUI = false;
    }

    ngOnInit() {
      this.loadAppointments();
    }
  
    loadAppointments(): void {
      this.blockUI = true;
      this._appointmentService.getAppointments().subscribe((paginatedAppointments: PaginatedAppointments): void => {
        this.blockUI = false;
        this.appointments = paginatedAppointments.appointment;
        console.log('Appointments : ', this.appointments[0]);
      }, (error: any): void => {
        this.blockUI = false;
        alert(error.message);
      });
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
