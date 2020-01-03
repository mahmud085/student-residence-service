import { ContractBase } from './contract-base.model';

export interface Contract extends ContractBase {
	contractId: string;
	createdOn: string;
}