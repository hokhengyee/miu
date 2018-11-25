import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MiuCourseModule } from './course/course.module';
import { MiuModuleModule } from './module/module.module';
import { MiuModuleTypeModule } from './module-type/module-type.module';
import { MiuForumRoomModule } from './forum-room/forum-room.module';
import { MiuForumRoomMessageModule } from './forum-room-message/forum-room-message.module';
import { MiuEntryQualificationModule } from './entry-qualification/entry-qualification.module';
import { MiuStaticPageTypeModule } from './static-page-type/static-page-type.module';
import { MiuStaticPageModule } from './static-page/static-page.module';
import { MiuResearchPaperModule } from './research-paper/research-paper.module';
import { MiuOnlineApplicationModule } from './online-application/online-application.module';
import { MiuPaymentTypeModule } from './payment-type/payment-type.module';
import { MiuRecordOfCertificateModule } from './record-of-certificate/record-of-certificate.module';
import { MiuStudentPaymentModule } from './student-payment/student-payment.module';
import { MiuSalutationModule } from './salutation/salutation.module';
import { MiuGenderModule } from './gender/gender.module';
import { MiuNewsAndEventModule } from './news-and-event/news-and-event.module';
import { MiuStudentProfileModule } from './student-profile/student-profile.module';
import { MiuGalleryModule } from './gallery/gallery.module';
import { MiuCourseAccessModule } from './course-access/course-access.module';
import { MiuExternalOnlineResourceModule } from './external-online-resource/external-online-resource.module';
import { MiuCommonResourcesModule } from './common-resources/common-resources.module';
import { MiuCourseMaterialModule } from './course-material/course-material.module';
import { MiuLecturerProfileModule } from './lecturer-profile/lecturer-profile.module';
import { MiuCustomStudentReportTypeModule } from './custom-student-report-type/custom-student-report-type.module';
import { MiuStudentModuleResultModule } from './student-module-result/student-module-result.module';
import { MiuStudentOtherResultModule } from './student-other-result/student-other-result.module';
import { MiuStudentResearchPaperResultModule } from './student-research-paper-result/student-research-paper-result.module';
import { MiuAdjunctFacultyModule } from './adjunct-faculty/adjunct-faculty.module';
import { MiuRegistrationAcademicDetailsModule } from './registration-academic-details/registration-academic-details.module';
import { MiuMinisterialWorkExperienceModule } from './ministerial-work-experience/ministerial-work-experience.module';
import { MiuCourseMaterialAccessModule } from './course-material-access/course-material-access.module';
import { MiuCourseModuleModule } from './course-module/course-module.module';
import { MiuAcademicCertificateModule } from './academic-certificate/academic-certificate.module';
import { MiuPageViewLogModule } from './page-view-log/page-view-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MiuCourseModule,
        MiuModuleModule,
        MiuModuleTypeModule,
        MiuForumRoomModule,
        MiuForumRoomMessageModule,
        MiuEntryQualificationModule,
        MiuStaticPageTypeModule,
        MiuStaticPageModule,
        MiuResearchPaperModule,
        MiuOnlineApplicationModule,
        MiuPaymentTypeModule,
        MiuRecordOfCertificateModule,
        MiuStudentPaymentModule,
        MiuSalutationModule,
        MiuGenderModule,
        MiuNewsAndEventModule,
        MiuStudentProfileModule,
        MiuGalleryModule,
        MiuCourseAccessModule,
        MiuExternalOnlineResourceModule,
        MiuCommonResourcesModule,
        MiuCourseMaterialModule,
        MiuLecturerProfileModule,
        MiuCustomStudentReportTypeModule,
        MiuStudentModuleResultModule,
        MiuStudentOtherResultModule,
        MiuStudentResearchPaperResultModule,
        MiuAdjunctFacultyModule,
        MiuRegistrationAcademicDetailsModule,
        MiuMinisterialWorkExperienceModule,
        MiuCourseMaterialAccessModule,
        MiuCourseModuleModule,
        MiuAcademicCertificateModule,
        MiuPageViewLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MiuEntityModule {}
