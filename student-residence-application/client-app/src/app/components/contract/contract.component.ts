import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Contract } from 'src/app/shared/models/contract.model';
import { ContractService } from 'src/app/services/contract.service';
import { UserRole } from 'src/app/shared/enums/user-role.enum';
import { ContractStatus } from 'src/app/shared/enums/contract-status.enum';
import { DropdownItem } from 'src/app/shared/models/dropdown-item.mode';
import { PaginatedContracts } from 'src/app/shared/models/paginated-contracts.model';
import { ContractUpdateOperation } from 'src/app/shared/enums/contract-update-operation.enum';
import { NewContract } from 'src/app/shared/models/new-contract.model';

@Component({
	selector: 'app-contract',
	templateUrl: './contract.component.html',
	styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit {

	newContract: NewContract;
	contracts: Contract[];
	blockUI: boolean;
	createContractValidator: any;
	updateContractValidator: any
	filterByNameText: string;
	updateContractTitle: string;
	contractUpdateContractId: string;
	contractUpdateOperation: ContractUpdateOperation;
	contractUpdateNewEndDate: string;

	get contractStatusOptions(): DropdownItem[] {
		let options: DropdownItem[] = [
			{
				value: '',
				label: 'Select a Status'
			}
		];

		Object.keys(ContractStatus).map((k: string): void => {
			options.push({
				value: ContractStatus[k],
				label: k
			});
		});

		return options;
	}

	constructor(private _authService: AuthService,
		private _contractService: ContractService) {

			this.blockUI = false;
			this.filterByNameText= null;
			
			this.resetCreateContractFields();
			this.resetCreateContractValidator();

			this.resetUpdateContractFields();
			this.resetUpdateContractValidator();
	}

	ngOnInit() {
		this.loadContracts();
	}

	loadContracts(): void {
		this.filterByNameText = null;
		this.blockUI = true;
		this._contractService.getContracts().subscribe((paginatedContracts: PaginatedContracts): void => {
			this.blockUI = false;
			this.contracts = paginatedContracts.contracts;
		}, (error: any): void => {
            this.blockUI = false;
            alert(error.status + " " + error.error);
		});
	}
	
	onClickFilterByContractorsName(): void {
		if (this.filterByNameText == null) {
			this.filterByNameText = '';
		}

		this.blockUI = true;
		this._contractService.getFilteredContracts(this.filterByNameText.trim()).subscribe((paginatedContracts: PaginatedContracts): void => {
			this.blockUI = false;
			this.contracts = paginatedContracts.contracts;
		}, (error: any): void => {
			this.blockUI = false;
			alert(error.status + " " + error.error);
		});
	}

	onClickSaveContract(): void {
		this.validateCreateContractFields();

		if (!this.areAllPropertiesTrue(this.createContractValidator)) {
			return;
		}

		this.blockUI = true;
		this._contractService.createContract(this.newContract).subscribe((contract: Contract): void => {
			this.blockUI = false;
			this.hideCreateContractModal();
			alert('Contract successfully created.');
			this.loadContracts();
		}, (error: any): void => {
			this.blockUI = false;
			alert(error.status + " " + error.error);
		});
	}

	onClickAcceptContract(contractId: string): void {
		this.blockUI = true;
		this._contractService.confirmContract(contractId).subscribe((msg: string): void => {			
			this.blockUI = false;
			alert('Contract successfully accepted.');
			this.loadContracts();
		}, (error: any): void => {
			this.blockUI = false;
			alert(error.status + " " + error.error);
		});
	}

	onClickUpdateContract(): void {
		this.validateUpdateContractFields();

		if (!this.areAllPropertiesTrue(this.updateContractValidator)) {
			return;
		}

		if (this.contractUpdateOperation == ContractUpdateOperation.Extend) {
			this.extendContract(this.contractUpdateContractId, this.contractUpdateNewEndDate);
		} else if (this.contractUpdateOperation == ContractUpdateOperation.Terminate) {
			this.terminateContract(this.contractUpdateContractId, this.contractUpdateNewEndDate);
		}
	}

	showCreateContractModal(): void {
		$('#createContractModal').modal({
			backdrop: 'static',
			keyboard: false
		});
		$("#createContractModal").modal().show();		
	}

	hideCreateContractModal(): void {
		this.resetCreateContractFields();
		this.resetCreateContractValidator();
		$('#close-new-contract-modal').click();
	}

	showUpdateContractModal(contractId: string, updateOperation: ContractUpdateOperation): void {
		switch (updateOperation) {
			case ContractUpdateOperation.Extend:				
				this.updateContractTitle = 'Extend Contract';
				break;
			case ContractUpdateOperation.Terminate:
				this.updateContractTitle = 'Terminate Contract';
				break;
			default:
				return;

		}

		this.contractUpdateContractId = contractId;
		this.contractUpdateOperation = updateOperation;

		$('#updateContractModal').modal({
			backdrop: 'static',
			keyboard: false
		});
		$("#updateContractModal").modal().show();		
	}

	hideUpdateContractModal(): void {
		this.resetUpdateContractFields();
		this.resetUpdateContractValidator();
		$('#close-update-contract-modal').click();
	}

	isUserAdministrator(): boolean {
		return this._authService.userCredential.role == UserRole.Admin;
	}

	isUserResident(): boolean {
		return this._authService.userCredential.role == UserRole.Resident;
	}

	isContractConfirmed(contract: Contract):boolean {
		return contract.status == ContractStatus.Confirmed;
	}

	private extendContract(contractId: string, newEndDate: string): void {
		this.blockUI = true;
		this._contractService.extendContract(contractId, newEndDate).subscribe((msg: string): void => {			
			this.blockUI = false;
			this.hideUpdateContractModal();
			alert('Contract successfully extended.');
			this.loadContracts();
		}, (error: any): void => {
			this.blockUI = false;
      alert(error.status + " " + error.error);
		});
	}

	private terminateContract(contractId: string, newEndDate: string): void {
		this.blockUI = true;
		this._contractService.terminateContract(contractId, newEndDate).subscribe((msg: string): void => {			
			this.blockUI = false;
			this.hideUpdateContractModal();
			alert('Contract successfully terminated.');
			this.loadContracts();
		}, (error: any): void => {
			this.blockUI = false;
			alert(error.status + " " + error.error);
		});
	}

	private validateCreateContractFields(): void {
		this.validateContractorsUserId();
		this.validateContractorsName();
		this.validateContractorsEmail();
		this.validateContractorsPhone();
		this.validateRoomNumber();
		this.validateStatus();
		this.validateStartDate();
		this.validateEndDate();
	}

	private validateContractorsUserId(): void {
		if (this.isNullOrEmpty(this.newContract.contractorsUserId)) {
			this.createContractValidator.isContractorsUserIdValid = false;
		} else {
			this.createContractValidator.isContractorsUserIdValid = true;
		}
	}

	private validateContractorsName(): void {
		if (this.isNullOrEmpty(this.newContract.contractorsName)) {
			this.createContractValidator.isContractorsNameValid = false;
		} else {
			this.createContractValidator.isContractorsNameValid = true;
		}
	}

	private validateContractorsEmail(): void {
		if (this.isNullOrEmpty(this.newContract.contractorsEmail)) {
			this.createContractValidator.isContractorsEmailValid = false;
		} else {
			this.createContractValidator.isContractorsEmailValid = true;
		}
	}

	private validateContractorsPhone(): void {
		if (this.isNullOrEmpty(this.newContract.contractorsPhone)) {
			this.createContractValidator.isContractorsPhoneValid = false;
		} else {
			this.createContractValidator.isContractorsPhoneValid = true;
		}
	}

	private validateRoomNumber(): void {
		if (this.isNullOrEmpty(this.newContract.roomNumber)) {
			this.createContractValidator.isRoomNumberValid = false;
		} else {
			this.createContractValidator.isRoomNumberValid = true;
		}
	}

	private validateStatus(): void {
		if (this.isNullOrEmpty(this.newContract.status)) {
			this.createContractValidator.isStatusValid = false;
		} else {
			this.createContractValidator.isStatusValid = true;
		}
	}

	private validateStartDate(): void {
		if (this.isNullOrEmpty(this.newContract.startDate)) {
			this.createContractValidator.isStartDateValid = false;
		} else {
			this.createContractValidator.isStartDateValid = true;
		}
	}

	private validateEndDate(): void {
		if (this.isNullOrEmpty(this.newContract.endDate)) {
			this.createContractValidator.isEndDateValid = false;
		} else {
			this.createContractValidator.isEndDateValid = true;
		}
	}

	private validateUpdateContractFields(): void {
		this.validateNewEndDate();
	}

	private validateNewEndDate():void {
		if (this.isNullOrEmpty(this.contractUpdateNewEndDate)) {
			this.updateContractValidator.isNewEndDateValid = false;
		} else {
			this.updateContractValidator.isNewEndDateValid = true;
		}
	}

	private resetCreateContractFields(): void {
		this.newContract = {
			contractorsUserId: null,
			contractorsName: null,
			contractorsEmail: null,
			contractorsPhone: null,
			roomNumber: null,
			endDate: null,
			startDate: null,
			status: null
		};
	}

	private resetCreateContractValidator(): void {
		this.createContractValidator = {
			isContractorsUserIdValid: true,
			isContractorsNameValid: true,
			isContractorsEmailValid: true,
			isContractorsPhoneValid: true,
			isRoomNumberValid: true,
			isStatusValid: true,
			isStartDateValid: true,
			isEndDateValid: true,
		}
	}

	private resetUpdateContractFields(): void {
		this.contractUpdateOperation = null;
		this.contractUpdateNewEndDate = null;
		this.updateContractTitle = null;
		this.contractUpdateContractId = null;
	}

	private resetUpdateContractValidator(): void {
		this.updateContractValidator = {
			isNewEndDateValid: true
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
