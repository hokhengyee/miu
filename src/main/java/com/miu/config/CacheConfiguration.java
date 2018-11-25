package com.miu.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.miu.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.miu.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.miu.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.miu.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.miu.domain.Course.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.Module.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.ModuleType.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.ForumRoom.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.ForumRoomMessage.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.EntryQualification.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StaticPageType.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StaticPage.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.ResearchPaper.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.OnlineApplication.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.PaymentType.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.RecordOfCertificate.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StudentPayment.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.Salutation.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.Gender.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.NewsAndEvent.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StudentProfile.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.Gallery.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CourseAccess.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.ExternalOnlineResource.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CommonResources.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CourseMaterial.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.LecturerProfile.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CustomStudentReportType.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StudentModuleResult.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StudentOtherResult.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.StudentResearchPaperResult.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.AdjunctFaculty.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.RegistrationAcademicDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.MinisterialWorkExperience.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CourseMaterialAccess.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.CourseModule.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.AcademicCertificate.class.getName(), jcacheConfiguration);
            cm.createCache(com.miu.domain.PageViewLog.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
