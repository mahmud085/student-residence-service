import { Component, OnInit, Input, SimpleChanges } from '@angular/core';
import { Login } from 'src/app/shared/models/login.model';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	@Input() loginModel: Login;
	returnUrl: string;
	pageValidator: any;
	blockUI: boolean;

	constructor(private _authService: AuthService,
		private _router: Router,
		private _route: ActivatedRoute) {

		this.loginModel = {
			userId: null,
			password: null,
			isPersistent: false
		};

		this.pageValidator = {
			isUserIdValid: true,
			isPasswordValid: true
		}

		this.blockUI = false
	}

	ngOnInit() {
		this.returnUrl = this._route.snapshot.queryParamMap.get('return-url');
	}

	onClickLogin(): void {
		let isPageInvalid: boolean = false;

		this.validateInputs();
		Object.keys(this.pageValidator).forEach((key): void => {
			if (!this.pageValidator[key]) {
				isPageInvalid = true;
			}
		});

		if (isPageInvalid) {
			return;
		}

		this.blockUI = true;
		this._authService.login(this.loginModel).subscribe((): void => {
            this.blockUI = false;
			if (this.returnUrl == null) {
				this.redirectToLandingPage();
			} else {
				this.redirectToReturnUrl();
			}
		}, (error: any): void => { });
	}

	validateInputs(): void {
		this.validateUserId();
		this.validatePassword();
	}

	validateUserId(): void {
		if (this.loginModel.userId == null || this.loginModel.userId == '') {
			this.pageValidator.isUserIdValid = false;
		} else {
			this.pageValidator.isUserIdValid = true;
		}
	}

	validatePassword(): void {
		if (this.loginModel.password == null || this.loginModel.password == '') {
			this.pageValidator.isPasswordValid = false;
		} else {
			this.pageValidator.isPasswordValid = true;
		}
	}

	private redirectToLandingPage(): void {
		this._router.navigateByUrl('/');
	}

	private redirectToReturnUrl(): void {
		this._router.navigateByUrl(`/${this.returnUrl}`);
	}
}
