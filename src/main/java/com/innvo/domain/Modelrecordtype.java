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
 * A Modelrecordtype.
 */
@Entity
@Table(name = "modelrecordtype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modelrecordtype")
public class Modelrecordtype implements Serializable {

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

    @OneToMany(mappedBy = "modelrecordtype")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Model> models = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Modelrecordtype name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameshort() {
        return nameshort;
    }

    public Modelrecordtype nameshort(String nameshort) {
        this.nameshort = nameshort;
        return this;
    }

    public void setNameshort(String nameshort) {
        this.nameshort = nameshort;
    }

    public String getDescription() {
        return description;
    }

    public Modelrecordtype description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Modelrecordtype status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public Modelrecordtype lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public Modelrecordtype lastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
        return this;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public Modelrecordtype domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<Model> getModels() {
        return models;
    }

    public Modelrecordtype models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public Modelrecordtype addModel(Model model) {
        this.models.add(model);
        model.setModelrecordtype(this);
        return this;
    }

    public Modelrecordtype removeModel(Model model) {
        this.models.remove(model);
        model.setModelrecordtype(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Modelrecordtype modelrecordtype = (Modelrecordtype) o;
        if (modelrecordtype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelrecordtype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Modelrecordtype{" +
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
