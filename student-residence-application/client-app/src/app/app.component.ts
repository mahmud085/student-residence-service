import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'student-residence';

	constructor(private _authService: AuthService) { }

	get isAuthenticated(): boolean {
		return this._authService.isAuthenticated();
	}
}

