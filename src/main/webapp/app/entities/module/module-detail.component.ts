import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModule } from 'app/shared/model/module.model';

@Component({
    selector: 'jhi-module-detail',
    templateUrl: './module-detail.component.html'
})
export class ModuleDetailComponent implements OnInit {
    module: IModule;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ module }) => {
            this.module = module;
        });
    }

    previousState() {
        window.history.back();
    }
}
