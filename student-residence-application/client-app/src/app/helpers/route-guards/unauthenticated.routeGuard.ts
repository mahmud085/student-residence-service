import { Injectable } from "@angular/core";
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Injectable()
export class UnauthenticatedRouteGuard implements CanActivate {

	constructor(private _authService: AuthService,
		private _router: Router) { }

	canActivate(route: ActivatedRouteSnapshot): boolean  {
		if (!this._authService.isAuthenticated()) {
			this._router.navigateByUrl(`/login?return-url=${route.url}`);

			return false;
		}
		
		return true;
	}
}