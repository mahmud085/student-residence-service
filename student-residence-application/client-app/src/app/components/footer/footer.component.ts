import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-footer',
	templateUrl: './footer.component.html',
	styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

	footerText: string;

	constructor() {
		this.footerText = null;
	}

	ngOnInit() {
		this.footerText = `Student Residence © ${(new Date()).getFullYear()}`;
	}
}
