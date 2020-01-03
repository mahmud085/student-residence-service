import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { 
	MatInputModule,
	MatButtonModule,
	MatIconModule,
	MatTableModule,
	MatSortModule,
	MatDialogModule,
	MatSnackBarModule,
	MatToolbarModule
} from '@angular/material';

import { FormsModule } from '@angular/forms';

import { AuthService } from './services/auth.service';
import { ContractService } from './services/contract.service';
import { AppointmentService } from './services/appointment.service';

import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ContractComponent } from './components/contract/contract.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';

import { Routes, RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpRequestInterceptor } from './helpers/http.interceptor';
import { AuthenticatedRouteGuard } from './helpers/route-guards/authenticated.routeGuard';
import { UnauthenticatedRouteGuard } from './helpers/route-guards/unauthenticated.routeGuard';
import { FooterComponent } from './components/footer/footer.component';
import { ConfigService } from './services/config.service';

export function initializeApp(configService: ConfigService) {
	return () => configService.load();
  }

const appRoutes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: '/home' },
	{ path: 'login', component: LoginComponent, pathMatch: 'full', canActivate: [ AuthenticatedRouteGuard ], data: { routeName: 'login' } },
	{ path: 'home', component: HomeComponent, pathMatch: 'full', canActivate: [ UnauthenticatedRouteGuard ], data: { routeName: 'home' } },
	{ path: 'contract', component: ContractComponent, pathMatch: 'full', canActivate: [ UnauthenticatedRouteGuard ], data: { routeName: 'contract' } },
	{ path: 'appointment', component: AppointmentComponent, pathMatch: 'full', canActivate: [ UnauthenticatedRouteGuard ], data: { routeName: 'appointment' } }
];

@NgModule({
	declarations: [
		AppComponent,
		NavBarComponent,
		LoginComponent,
		HomeComponent,
		ContractComponent,
		AppointmentComponent,
		FooterComponent
  	],
  	imports: [
		RouterModule.forRoot(
			appRoutes,
			{ enableTracing: true } // <-- debugging purposes only
		),

    	BrowserModule,
    	AppRoutingModule,
		BrowserAnimationsModule,

		MatInputModule,
		MatButtonModule,
		MatIconModule,
		MatTableModule,
		MatSortModule,
		MatDialogModule,
		MatSnackBarModule,
		MatToolbarModule,

		FormsModule,

		HttpClientModule
  	],
  	providers: [
		{ 
			provide: HTTP_INTERCEPTORS, 
			useClass: HttpRequestInterceptor,
			multi: true 
		},
		{ 
			provide: APP_INITIALIZER,
			useFactory: initializeApp,
			deps: [ConfigService],
			multi: true
		},
		AuthService,
		ContractService,
		AppointmentService,
		ConfigService,
		AuthenticatedRouteGuard,
		UnauthenticatedRouteGuard
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
