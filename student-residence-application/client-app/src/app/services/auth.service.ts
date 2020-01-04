import { Injectable } from '@angular/core';
import { Login } from '../shared/models/login.model';
import { Observable } from 'rxjs/internal/Observable';
import { UserRole } from '../shared/enums/user-role.enum';
import { UserCredential } from '../shared/models/user-credential.model';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	private credential: UserCredential;

	get userCredential(): UserCredential {
		let credential: string | null = window.localStorage.getItem('credential');

		if (credential != null) {
			return JSON.parse(credential) as UserCredential;
		}

		return null;
	}

	constructor() {
		this.credential = null;
	}	

	login(loginModel: Login): Observable<void> {
		return new Observable<void>(observer => {
            setTimeout(() => {
				this.credential = {
					userId: 'dummy',
					name: 'Resident User 1',
					accessToken: 'dummy',
					role: UserRole.Caretaker				
				}

				if (loginModel.isPersistent) {
					window.localStorage.setItem('credential', JSON.stringify(this.credential));
				} else {
					window.localStorage.setItem('credential', JSON.stringify(this.credential));
				}

                observer.next();
            }, 1500);
		});
	}

	logout(): Observable<void> {
		return new Observable<void>(observer => {
            setTimeout(() => {
				window.localStorage.removeItem('credential');
				window.sessionStorage.removeItem('credential');

                observer.next();
            }, 1500);
		});
	}

	isAuthenticated(): boolean {
		return window.localStorage.getItem('credential') != null || window.sessionStorage.getItem('credential') != null;
	}
}
