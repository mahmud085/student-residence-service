import { Injectable } from '@angular/core';
import { AppConfig } from '../shared/models/app-config.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
	providedIn: 'root'
})
export class ConfigService {
	static appConfig: AppConfig;

	constructor(private _http: HttpClient) { }
	
    load(): Promise<void> {
        let jsonFile = 'assets/config/config.json';
        return new Promise<void>((resolve, reject) => {
            this._http.get(jsonFile).toPromise().then((response : AppConfig) => {
                ConfigService.appConfig = <AppConfig>response;
               resolve();
            }).catch((response: any) => {
               reject();
            });
        });
    }
}
