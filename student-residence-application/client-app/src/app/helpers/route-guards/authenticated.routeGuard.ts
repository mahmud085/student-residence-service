import { Injectable } from "@angular/core";
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Injectable()
export class AuthenticatedRouteGuard implements CanActivate {

	constructor(private _authService: AuthService,
		private _router: Router) { }

	canActivate(route: ActivatedRouteSnapshot): boolean  {
		if (this._authService.isAuthenticated()) {
			this._router.navigateByUrl('/');

			return false;
		}
		
		return true;
	}
}