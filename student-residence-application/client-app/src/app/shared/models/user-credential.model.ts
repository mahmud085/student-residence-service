import { UserRole } from '../enums/user-role.enum';

export interface UserCredential {
	userId: string;
	name: string;
	role: UserRole;
	accessToken: string;
}