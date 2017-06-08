package com.innvo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Assetjson.
 */
@Entity
@Table(name = "assetjson")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetjson")
public class Assetjson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "arrayname", length = 50)
    private String arrayname;

    @Size(max = 20)
    @Column(name = "arraynameshort", length = 20)
    private String arraynameshort;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "details")
    @Type(type = "StringJsonObject")
    private String details;

    @NotNull
    @Size(max = 50)
    @Column(name = "lastmodifiedby", length = 50, nullable = false)
    private String lastmodifiedby;

    @NotNull
    @Column(name = "lastmodifieddatetime", nullable = false)
    private ZonedDateTime lastmodifieddatetime;

    @NotNull
    @Size(max = 50)
    @Column(name = "domain", length = 50, nullable = false)
    private String domain;

    @ManyToOne
    private Asset asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArrayname() {
        return arrayname;
    }

    public Assetjson arrayname(String arrayname) {
        this.arrayname = arrayname;
        return this;
    }

    public void setArrayname(String arrayname) {
        this.arrayname = arrayname;
    }

    public String getArraynameshort() {
        return arraynameshort;
    }

    public Assetjson arraynameshort(String arraynameshort) {
        this.arraynameshort = arraynameshort;
        return this;
    }

    public void setArraynameshort(String arraynameshort) {
        this.arraynameshort = arraynameshort;
    }

    public String getDescription() {
        return description;
    }

    public Assetjson description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public Assetjson details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public Assetjson lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public Assetjson lastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
        return this;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public Assetjson domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Asset getAsset() {
        return asset;
    }

    public Assetjson asset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assetjson assetjson = (Assetjson) o;
        if (assetjson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetjson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assetjson{" +
            "id=" + getId() +
            ", arrayname='" + getArrayname() + "'" +
            ", arraynameshort='" + getArraynameshort() + "'" +
            ", description='" + getDescription() + "'" +
            ", details='" + getDetails() + "'" +
            ", lastmodifiedby='" + getLastmodifiedby() + "'" +
            ", lastmodifieddatetime='" + getLastmodifieddatetime() + "'" +
            ", domain='" + getDomain() + "'" +
            "}";
    }
}
