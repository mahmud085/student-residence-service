import { ContractUpdateOperation } from '../enums/contract-update-operation.enum';

export interface UpdateContract {
	operation: ContractUpdateOperation;
	endDate: string;
}