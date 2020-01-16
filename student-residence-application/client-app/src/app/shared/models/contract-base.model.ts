import { ContractStatus } from '../enums/contract-status.enum';

export interface ContractBase {
	contractorsUserId: string;
	contractorsName: string;
	contractorsEmail: string;
	contractorsPhone: string;
	roomNumber: string;
	startDate: string;
	endDate: string;
	status: ContractStatus;
}