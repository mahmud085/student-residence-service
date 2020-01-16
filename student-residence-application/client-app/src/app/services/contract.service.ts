import { Injectable } from '@angular/core';
import { Contract } from '../shared/models/contract.model';
import { Observable } from 'rxjs';
import { ConfigService } from './config.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PaginatedContracts } from '../shared/models/paginated-contracts.model';
import { AuthService } from './auth.service';
import { UserRole } from '../shared/enums/user-role.enum';
import { UpdateContract } from '../shared/models/update-contract.model';
import { ContractUpdateOperation } from '../shared/enums/contract-update-operation.enum';
import { NewContract } from '../shared/models/new-contract.model';

@Injectable({
	providedIn: 'root'
})
export class ContractService {

	constructor(private _authService: AuthService,
		private _httpClient: HttpClient) { }

	getContracts(): Observable<PaginatedContracts> {
		let requestUrl: string = null;
		
		switch (this._authService.userCredential.role) {
			case UserRole.Admin:
				requestUrl = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts`;
				break;
			case UserRole.Resident:
				requestUrl = `${ConfigService.appConfig.service.contract.baseUrl}/api/contractors/${this._authService.userCredential.userId}/contracts`;
		}

		return this._httpClient.get<PaginatedContracts>(requestUrl);
	}

	getFilteredContracts(filterByNameText: string): Observable<PaginatedContracts> {
		let requestUrl: string = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts?contractorsName=${filterByNameText}`;		

		return this._httpClient.get<PaginatedContracts>(requestUrl);
	}

	createContract(newContract: NewContract): Observable<Contract> {
		let requestUrl: string = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts`

		return this._httpClient.post<Contract>(requestUrl, newContract);
	}

	confirmContract(contractId: string): Observable<string> {
		let confirmContractRequest: UpdateContract = {
			operation: ContractUpdateOperation.Confirm,
			endDate: null
		};

		let requestUrl: string = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts/${contractId}`;

		return this._httpClient.patch<string>(requestUrl, confirmContractRequest, {
			responseType: 'text' as 'json'
		});
	}

	extendContract(contractId: string, newEndDate: string): Observable<string> {
		let confirmContractRequest: UpdateContract = {
			operation: ContractUpdateOperation.Extend,
			endDate: newEndDate
		};

		let requestUrl: string = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts/${contractId}`;

		return this._httpClient.patch<string>(requestUrl, confirmContractRequest, {
			responseType: 'text' as 'json'
		});
	}

	terminateContract(contractId: string, newEndDate: string): Observable<string> {
		let confirmContractRequest: UpdateContract = {
			operation: ContractUpdateOperation.Terminate,
			endDate: newEndDate
		};

		let requestUrl: string = `${ConfigService.appConfig.service.contract.baseUrl}/api/contracts/${contractId}`;

		return this._httpClient.patch<string>(requestUrl, confirmContractRequest, {
			responseType: 'text' as 'json'
		});
	}
}
