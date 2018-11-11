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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewsAndEvent newsAndEvent = (NewsAndEvent) o;
        if (newsAndEvent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, newsAndEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NewsAndEvent{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", websiteLink='" + websiteLink + "'" +
            ", startDT='" + startDT + "'" +
            ", endDT='" + endDT + "'" +
            ", venue='" + venue + "'" +
            ", eventDetail='" + eventDetail + "'" +
            '}';
    }
}
