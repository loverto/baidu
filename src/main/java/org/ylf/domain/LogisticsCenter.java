package org.ylf.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物流中心\n@author ylf
 */
@ApiModel(description = "物流中心\n@author ylf")
@Entity
@Table(name = "logistics_center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogisticsCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 行政区名称
     */
    @ApiModelProperty(value = "行政区名称")
    @Column(name = "region_name")
    private String regionName;

    /**
     * 物流中心名称
     */
    @ApiModelProperty(value = "物流中心名称")
    @Column(name = "logistics_center_name")
    private String logisticsCenterName;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @Column(name = "longitude")
    private String longitude;

    /**
     * 维度
     */
    @ApiModelProperty(value = "维度")
    @Column(name = "dimension")
    private String dimension;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @Column(name = "creation_by")
    private Integer creationBy;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @Column(name = "creation_date")
    private Instant creationDate;

    /**
     * 最后修改人
     */
    @ApiModelProperty(value = "最后修改人")
    @Column(name = "last_modified_by")
    private Integer lastModifiedBy;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间")
    @Column(name = "last_modify_time")
    private Instant lastModifyTime;

    /**
     * 最后修改日期
     */
    @ApiModelProperty(value = "最后修改日期")
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    @Column(name = "available")
    private Integer available;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public LogisticsCenter regionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLogisticsCenterName() {
        return logisticsCenterName;
    }

    public LogisticsCenter logisticsCenterName(String logisticsCenterName) {
        this.logisticsCenterName = logisticsCenterName;
        return this;
    }

    public void setLogisticsCenterName(String logisticsCenterName) {
        this.logisticsCenterName = logisticsCenterName;
    }

    public String getLongitude() {
        return longitude;
    }

    public LogisticsCenter longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDimension() {
        return dimension;
    }

    public LogisticsCenter dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getCreationBy() {
        return creationBy;
    }

    public LogisticsCenter creationBy(Integer creationBy) {
        this.creationBy = creationBy;
        return this;
    }

    public void setCreationBy(Integer creationBy) {
        this.creationBy = creationBy;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public LogisticsCenter creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public LogisticsCenter lastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifyTime() {
        return lastModifyTime;
    }

    public LogisticsCenter lastModifyTime(Instant lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public void setLastModifyTime(Instant lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public LogisticsCenter lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getAvailable() {
        return available;
    }

    public LogisticsCenter available(Integer available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogisticsCenter)) {
            return false;
        }
        return id != null && id.equals(((LogisticsCenter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogisticsCenter{" +
            "id=" + getId() +
            ", regionName='" + getRegionName() + "'" +
            ", logisticsCenterName='" + getLogisticsCenterName() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", dimension='" + getDimension() + "'" +
            ", creationBy=" + getCreationBy() +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", available=" + getAvailable() +
            "}";
    }
}
