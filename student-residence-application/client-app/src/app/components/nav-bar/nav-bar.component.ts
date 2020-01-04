import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/shared/models/page.model';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { UserRole } from 'src/app/shared/enums/user-role.enum';

@Component({
	selector: 'app-nav-bar',
	templateUrl: './nav-bar.component.html',
	styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

	blockUI: boolean;
	private _navigationPages: Page[];

	constructor(private _router: Router,
		private _authService: AuthService) {
			
			this.blockUI = false;
			this._navigationPages = [
				{
					name: 'home',
					title: 'Home',
					url: '/home',
					roles:  [ UserRole.Admin, UserRole.Caretaker, UserRole.Resident ]
				},
				{
					name: 'contract',
					title: 'Contract',
					url: '/contract',
					roles:  [ UserRole.Admin , UserRole.Resident ]
				},
				{
					name: 'appointment',
					title: 'Appointment',
					url: '/appointment',
					roles:  [ UserRole.Admin , UserRole.Caretaker, UserRole.Resident ]
				}				
			];
		}

	get navigationPages(): Page[] {
		return this._navigationPages.filter((p: Page): boolean => {
            console.log(p.name + p.roles)
			return p.roles.indexOf(this._authService.userCredential.role) > -1;
		});
	}

	ngOnInit() {		
	}

	onClickLogout(): void {
		this.blockUI = true;
		this._authService.logout().subscribe((): void => {
			this.blockUI = false;
            this.navigateToLoginPage();
		}, (error: any): void => { });
	}

	hasUserAccessForThisPage(page: Page): boolean {
		if (page.roles.indexOf(this._authService.userCredential.role) > -1) {
			return true;
		}

		return false;
	}

	private navigateToLoginPage(): void {
		this._router.navigateByUrl('/login');
	}
}
