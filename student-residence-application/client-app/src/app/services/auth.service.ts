import { Injectable } from '@angular/core';
import { Login } from '../shared/models/login.model';
import { Observable } from 'rxjs/internal/Observable';
import { UserCredential } from '../shared/models/user-credential.model';
import { ConfigService } from './config.service';
import { HttpClient } from '@angular/common/http';
import { LoginResponse } from '../shared/models/login-response.model';
import { User } from '../shared/models/user.model';

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

	constructor(private _httpClient: HttpClient) {
		this.credential = null;
	}	

	login(loginModel: Login): Observable<void> {
        let isPersistent: boolean = loginModel.isPersistent;
        let loginRequestUrl: string = `${ConfigService.appConfig.service.auth.baseUrl}/api/authentication/login`;
        delete loginModel.isPersistent;

        

		return new Observable<void>(observer => {
            this._httpClient.post<LoginResponse>(loginRequestUrl, loginModel).subscribe((loginResponse: LoginResponse): void => {
                let validateAccessTokenRequestUrl: string = `${ConfigService.appConfig.service.auth.baseUrl}/api/authentication/accessToken/${loginResponse.accessToken}/validation`;
                this._httpClient.get<User>(validateAccessTokenRequestUrl).subscribe((user: User): void => {
                    this.credential = {
                        userId: user.userId,
                        name: user.userName,
                        accessToken: loginResponse.accessToken,
                        role: user.userType
                    }

                    	if (loginModel.isPersistent) {
                            window.localStorage.setItem('credential', JSON.stringify(this.credential));
                        } else {
                            window.sessionStorage.setItem('credential', JSON.stringify(this.credential));
                        }

                    observer.next();
                }, (error: any): void => {
                    observer.error(error);
                });
            }, (error: any): void => {
                observer.error(error);
            });
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
