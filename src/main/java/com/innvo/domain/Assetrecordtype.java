package com.innvo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Assetrecordtype.
 */
@Entity
@Table(name = "assetrecordtype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetrecordtype")
public class Assetrecordtype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "nameshort", length = 20, nullable = false)
    private String nameshort;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 25)
    @Column(name = "status", length = 25)
    private String status;

    @Size(max = 50)
    @Column(name = "lastmodifiedby", length = 50)
    private String lastmodifiedby;

    @Column(name = "lastmodifieddatetime")
    private ZonedDateTime lastmodifieddatetime;

    @Size(max = 25)
    @Column(name = "domain", length = 25)
    private String domain;

    @OneToMany(mappedBy = "assetrecordtype")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Asset> assets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Assetrecordtype name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameshort() {
        return nameshort;
    }

    public Assetrecordtype nameshort(String nameshort) {
        this.nameshort = nameshort;
        return this;
    }

    public void setNameshort(String nameshort) {
        this.nameshort = nameshort;
    }

    public String getDescription() {
        return description;
    }

    public Assetrecordtype description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Assetrecordtype status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public Assetrecordtype lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public Assetrecordtype lastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
        return this;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public Assetrecordtype domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public Assetrecordtype assets(Set<Asset> assets) {
        this.assets = assets;
        return this;
    }

    public Assetrecordtype addAsset(Asset asset) {
        this.assets.add(asset);
        asset.setAssetrecordtype(this);
        return this;
    }

    public Assetrecordtype removeAsset(Asset asset) {
        this.assets.remove(asset);
        asset.setAssetrecordtype(null);
        return this;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assetrecordtype assetrecordtype = (Assetrecordtype) o;
        if (assetrecordtype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetrecordtype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assetrecordtype{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameshort='" + getNameshort() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastmodifiedby='" + getLastmodifiedby() + "'" +
            ", lastmodifieddatetime='" + getLastmodifieddatetime() + "'" +
            ", domain='" + getDomain() + "'" +
            "}";
    }
}
