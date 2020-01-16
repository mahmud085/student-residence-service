import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

	constructor(private _authService: AuthService) { }

	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if (this._authService.isAuthenticated()) {
			req = req.clone({
				headers: new HttpHeaders({					
					'Authorization': `Bearer ${this._authService.userCredential.accessToken}`
				})
			});
		}
		
		return next.handle(req);
	}
}
