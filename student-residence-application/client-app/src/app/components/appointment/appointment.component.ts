import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserRole } from 'src/app/shared/enums/user-role.enum';
import { Appointment } from 'src/app/shared/models/appointment.model';
import { AppointmentStatus } from 'src/app/shared/enums/appointment-status.enum';
import { DropdownItem } from 'src/app/shared/models/dropdown-item.mode';
import { AppointmentType } from 'src/app/shared/enums/appointment-type.enum';
import { PaginatedAppointments } from 'src/app/shared/models/paginated-appointments.model';
import { NewAppointment } from 'src/app/shared/models/new-appointment.model';
import { AppointmentPriority } from 'src/app/shared/enums/appointment-priority.enum';

@Component({
	selector: 'app-appointment',
	templateUrl: './appointment.component.html',
	styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  newAppointment: NewAppointment;
  appointments: Appointment[];
  blockUI: boolean;
  createAppointmentValidator: any;
  
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

		Object.keys(AppointmentPriority).map((k: string): void => {
			options.push({
				value: AppointmentPriority[k],
				label: k
			});
		});

		return options;
	}

	constructor(private _authService: AuthService,
  private _appointmentService: AppointmentService) { 
    this.blockUI = false;

    this.resetCreateAppointmentFields();
    this.resetCreateAppointmentValidator();
  }

  ngOnInit() {
    this.loadAppointments();
  }

  loadAppointments(): void {
    this.blockUI = true;
    this._appointmentService.getAppointments().subscribe((paginatedAppointments: PaginatedAppointments): void => {
      this.blockUI = false;
      this.appointments = paginatedAppointments.appointment;
    }, (error: any): void => {
      this.blockUI = false;
      alert(error.message);
    });
  }

  onClickSaveAppointment(): void {
    this.validateCreateAppointmentFields();
    
    this.blockUI = true;
    this._appointmentService.createAppointment(this.newAppointment).subscribe((appointment: Appointment): void => {
      this.blockUI = false;
      this.hideCreateAppointmnetModal();
      alert('Appointment successfully created.');
      this.loadAppointments();
    }, (error: any): void => {
      this.blockUI = false;
      alert(error.error);
    });
  }

  onClickAcceptAppointment(appointmentId: string): void {
    this.blockUI = true;
    this._appointmentService.acceptAppointment(appointmentId).subscribe((msg: string): void => {			
      this.blockUI = false;
      alert('Appointment successfully accepted.');
      this.loadAppointments();
    }, (error: any): void => {
      this.blockUI = false;
      console.log('Error accept ', error);
      alert(error.message);
    });
  }

  onClickCancelAppointment(appointmentId: string): void {
    this.blockUI = true;
    this._appointmentService.cancelAppointment(appointmentId).subscribe((msg: string): void => {			
      this.blockUI = false;
      alert('Appointment canceled.');
      this.loadAppointments();
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
    this.resetCreateAppointmentFields();
    this.resetCreateAppointmentValidator();
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
  
	private resetCreateAppointmentFields(): void {
		this.newAppointment = {
			contractId: null,
			contractorsName: null,
      roomNumber: null,
      appointmentType: null,
      issue: null,
      priority: null,
			desiredDate: null
		};
  }
  
  private resetCreateAppointmentValidator(): void {
		this.createAppointmentValidator = {
			isContractIdValid: true,
			isContractorsNameValid: true,
			isAppointmentTypeValid: true,
			isAppointmentDesiredDateValid: true,
			isRoomNumberValid: true,
			isIssueValid: true,
			isAppointmentPriorityValid: true
		}
  }
  
  private validateCreateAppointmentFields(): void {
		this.validateContractId();
		this.validateContractorsName();
		this.validateRoomNumber();
		this.validateAppointmentType();
		this.validateDesiredDate();
    this.validateIssue();
    this.validatePriority();
  }
  
  private validateContractId(): void {
		if (this.isNullOrEmpty(this.newAppointment.contractId)) {
			this.createAppointmentValidator.isContractIdValid = false;
		} else {
			this.createAppointmentValidator.isContractIdValid = true;
		}
	}

	private validateContractorsName(): void {
		if (this.isNullOrEmpty(this.newAppointment.contractorsName)) {
			this.createAppointmentValidator.isContractorsNameValid = false;
		} else {
			this.createAppointmentValidator.isContractorsNameValid = true;
		}
	}

	private validateAppointmentType(): void {
		if (this.isNullOrEmpty(this.newAppointment.appointmentType)) {
			this.createAppointmentValidator.isAppointmentTypeValid = false;
		} else {
			this.createAppointmentValidator.isAppointmentTypeValid = true;
		}
	}

	private validateDesiredDate(): void {
		if (this.isNullOrEmpty(this.newAppointment.desiredDate)) {
			this.createAppointmentValidator.isAppointmentDesiredDateValid = false;
		} else {
			this.createAppointmentValidator.isAppointmentDesiredDateValid = true;
		}
	}

	private validateRoomNumber(): void {
		if (this.isNullOrEmpty(this.newAppointment.roomNumber)) {
			this.createAppointmentValidator.isRoomNumberValid = false;
		} else {
			this.createAppointmentValidator.isRoomNumberValid = true;
		}
	}

	private validateIssue(): void {
		if (this.isNullOrEmpty(this.newAppointment.issue)) {
			this.createAppointmentValidator.isIssueValid = false;
		} else {
			this.createAppointmentValidator.isIssueValid = true;
		}
	}

	private validatePriority(): void {
		if (this.isNullOrEmpty(this.newAppointment.priority)) {
			this.createAppointmentValidator.isAppointmentPriorityValid = false;
		} else {
			this.createAppointmentValidator.isAppointmentPriorityValid = true;
		}
	}

  private isNullOrEmpty(val: string): boolean {
		return val == null || val == '';
	}

	private areAllPropertiesTrue(obj: any): boolean {
		for (var key in obj) {
			if (obj.hasOwnProperty(key)) {
				if (obj[key] != true) {
					return false;
				}
			}
		}

		return true;
	}
}
