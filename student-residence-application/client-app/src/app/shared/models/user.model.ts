import { UserRole } from '../enums/user-role.enum';

export interface User {
    userId: string;
    userName: string;
    userType: UserRole;
}