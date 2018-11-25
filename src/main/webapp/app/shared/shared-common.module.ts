import { NgModule } from '@angular/core';

import { MiuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MiuSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MiuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MiuSharedCommonModule {}
