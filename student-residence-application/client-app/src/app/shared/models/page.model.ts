import { UserRole } from '../enums/user-role.enum';

export interface Page {
	name: string;
	title: string;
	url: string;
	roles: UserRole[]
}