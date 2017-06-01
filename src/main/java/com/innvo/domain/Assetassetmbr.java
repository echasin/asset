package com.innvo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Assetassetmbr.
 */
@Entity
@Table(name = "assetassetmbr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assetassetmbr")
public class Assetassetmbr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "comment", length = 100)
    private String comment;

    @Column(name = "parentxcoordinate")
    private Integer parentxcoordinate;

    @Column(name = "parentycoordinate")
    private Integer parentycoordinate;

    @Column(name = "childxcoordinate")
    private Integer childxcoordinate;

    @Column(name = "childycoordinate")
    private Integer childycoordinate;

    
    @Size(max = 100)
    @Column(name = "parentinstance", length = 100)
    private String parentinstance;

    @Size(max = 100)
    @Column(name = "childinstance", length = 100)
    private String childinstance;

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

    @ManyToOne(optional = false)
    @NotNull
    private Asset parentasset;

    @ManyToOne(optional = false)
    @NotNull
    private Asset childasset;

    @ManyToOne(optional = false)
    @NotNull
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Assetassetmbr comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

  

    public Integer getParentxcoordinate() {
		return parentxcoordinate;
	}

	public void setParentxcoordinate(Integer parentxcoordinate) {
		this.parentxcoordinate = parentxcoordinate;
	}

	public Integer getParentycoordinate() {
		return parentycoordinate;
	}

	public void setParentycoordinate(Integer parentycoordinate) {
		this.parentycoordinate = parentycoordinate;
	}

	public Integer getChildxcoordinate() {
		return childxcoordinate;
	}

	public void setChildxcoordinate(Integer childxcoordinate) {
		this.childxcoordinate = childxcoordinate;
	}

	public Integer getChildycoordinate() {
		return childycoordinate;
	}

	public void setChildycoordinate(Integer childycoordinate) {
		this.childycoordinate = childycoordinate;
	}

	public String getParentinstance() {
        return parentinstance;
    }

    public Assetassetmbr parentinstance(String parentinstance) {
        this.parentinstance = parentinstance;
        return this;
    }

    public void setParentinstance(String parentinstance) {
        this.parentinstance = parentinstance;
    }

    public String getChildinstance() {
        return childinstance;
    }

    public Assetassetmbr childinstance(String childinstance) {
        this.childinstance = childinstance;
        return this;
    }

    public void setChildinstance(String childinstance) {
        this.childinstance = childinstance;
    }

    public String getDescription() {
        return description;
    }

    public Assetassetmbr description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public Assetassetmbr status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public Assetassetmbr lastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
        return this;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public Assetassetmbr lastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
        return this;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public Assetassetmbr domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Asset getParentasset() {
        return parentasset;
    }

    public Assetassetmbr parentasset(Asset asset) {
        this.parentasset = asset;
        return this;
    }

    public void setParentasset(Asset asset) {
        this.parentasset = asset;
    }

    public Asset getChildasset() {
        return childasset;
    }

    public Assetassetmbr childasset(Asset asset) {
        this.childasset = asset;
        return this;
    }

    public void setChildasset(Asset asset) {
        this.childasset = asset;
    }

    public Model getModel() {
        return model;
    }

    public Assetassetmbr model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assetassetmbr assetassetmbr = (Assetassetmbr) o;
        if (assetassetmbr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetassetmbr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "Assetassetmbr [id=" + id + ", comment=" + comment + ", parentxcoordinate=" + parentxcoordinate
				+ ", parentycoordinate=" + parentycoordinate + ", childxcoordinate=" + childxcoordinate
				+ ", childycoordinate=" + childycoordinate + ", parentinstance=" + parentinstance + ", childinstance="
				+ childinstance + ", description=" + description + ", status=" + status
				+ ", lastmodifiedby=" + lastmodifiedby + ", lastmodifieddatetime=" + lastmodifieddatetime + ", domain="
				+ domain + ", parentasset=" + parentasset + ", childasset=" + childasset + ", model=" + model + "]";
	}

    
}
