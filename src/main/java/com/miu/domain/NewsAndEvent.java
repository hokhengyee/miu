package com.miu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A NewsAndEvent.
 */
@Entity
@Table(name = "news_and_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NewsAndEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 3000)
    @Column(name = "title", length = 3000, nullable = false)
    private String title;

    @Size(max = 3000)
    @Column(name = "website_link", length = 3000)
    private String websiteLink;

    @Column(name = "start_dt")
    private ZonedDateTime startDT;

    @Column(name = "end_dt")
    private ZonedDateTime endDT;

    @Column(name = "venue")
    private String venue;

    @Lob
    @Column(name = "event_detail")
    private String eventDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public NewsAndEvent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public NewsAndEvent websiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
        return this;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public ZonedDateTime getStartDT() {
        return startDT;
    }

    public NewsAndEvent startDT(ZonedDateTime startDT) {
        this.startDT = startDT;
        return this;
    }

    public void setStartDT(ZonedDateTime startDT) {
        this.startDT = startDT;
    }

    public ZonedDateTime getEndDT() {
        return endDT;
    }

    public NewsAndEvent endDT(ZonedDateTime endDT) {
        this.endDT = endDT;
        return this;
    }

    public void setEndDT(ZonedDateTime endDT) {
        this.endDT = endDT;
    }

    public String getVenue() {
        return venue;
    }

    public NewsAndEvent venue(String venue) {
        this.venue = venue;
        return this;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public NewsAndEvent eventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
        return this;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewsAndEvent newsAndEvent = (NewsAndEvent) o;
        if (newsAndEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newsAndEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewsAndEvent{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", websiteLink='" + getWebsiteLink() + "'" +
            ", startDT='" + getStartDT() + "'" +
            ", endDT='" + getEndDT() + "'" +
            ", venue='" + getVenue() + "'" +
            ", eventDetail='" + getEventDetail() + "'" +
            "}";
    }
}
