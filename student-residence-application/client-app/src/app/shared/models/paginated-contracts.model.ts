import { PaginationMetadata } from './pagination-metadata.model';
import { Contract } from './contract.model';

export interface PaginatedContracts {
	metadata: PaginationMetadata;
	contracts: Contract[];
}