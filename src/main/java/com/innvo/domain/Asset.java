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
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "asset")
public class Asset implements Serializable {

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

    @NotNull
    @Size(max = 25)
    @Column(name = "status", length = 25, nullable = false)
    private String status;

    @NotNull
    @Size(max = 50)
    @Column(name = "lastmodifiedby", length = 50, nullable = false)
    private String lastmodifiedby;

    @NotNull
    @Column(name = "lastmodifieddatetime", nullable = false)
    private ZonedDateTime lastmodifieddatetime;

    @Size(max = 25)
    @Column(name = "domain", length = 25)
    private String domain;

    @ManyToOne
    private Assetrecordtype assetrecordtype;

    @OneToMany(mappedBy = "parentasset")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Assetassetmbr> parentassets = new HashSet<>();

    @OneToMany(mappedBy = "childasset")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Assetassetmbr> childassets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameshort() {
        return nameshort;
    }

    public Asset nameshort(String nameshort) {
        this.nameshort = nameshort;
        return this;
    }

    public void setNameshort(String nameshort) {
        this.nameshort = nameshort;
    }

    public String getDescription() {
        return description;
    }

    public Asset description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Asset status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public Asset lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public Asset lastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
        return this;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public Asset domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Assetrecordtype getAssetrecordtype() {
        return assetrecordtype;
    }

    public Asset assetrecordtype(Assetrecordtype assetrecordtype) {
        this.assetrecordtype = assetrecordtype;
        return this;
    }

    public void setAssetrecordtype(Assetrecordtype assetrecordtype) {
        this.assetrecordtype = assetrecordtype;
    }

    public Set<Assetassetmbr> getParentassets() {
        return parentassets;
    }

    public Asset parentassets(Set<Assetassetmbr> assetassetmbrs) {
        this.parentassets = assetassetmbrs;
        return this;
    }

    public Asset addParentasset(Assetassetmbr assetassetmbr) {
        this.parentassets.add(assetassetmbr);
        assetassetmbr.setParentasset(this);
        return this;
    }

    public Asset removeParentasset(Assetassetmbr assetassetmbr) {
        this.parentassets.remove(assetassetmbr);
        assetassetmbr.setParentasset(null);
        return this;
    }

    public void setParentassets(Set<Assetassetmbr> assetassetmbrs) {
        this.parentassets = assetassetmbrs;
    }

    public Set<Assetassetmbr> getChildassets() {
        return childassets;
    }

    public Asset childassets(Set<Assetassetmbr> assetassetmbrs) {
        this.childassets = assetassetmbrs;
        return this;
    }

    public Asset addChildasset(Assetassetmbr assetassetmbr) {
        this.childassets.add(assetassetmbr);
        assetassetmbr.setChildasset(this);
        return this;
    }

    public Asset removeChildasset(Assetassetmbr assetassetmbr) {
        this.childassets.remove(assetassetmbr);
        assetassetmbr.setChildasset(null);
        return this;
    }

    public void setChildassets(Set<Assetassetmbr> assetassetmbrs) {
        this.childassets = assetassetmbrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        if (asset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asset{" +
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
