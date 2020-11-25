package org.ylf.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link org.ylf.domain.LogisticsCenter} entity. This class is used
 * in {@link org.ylf.web.rest.LogisticsCenterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /logistics-centers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LogisticsCenterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter regionName;

    private StringFilter logisticsCenterName;

    private StringFilter longitude;

    private StringFilter dimension;

    private IntegerFilter creationBy;

    private InstantFilter creationDate;

    private IntegerFilter lastModifiedBy;

    private InstantFilter lastModifyTime;

    private InstantFilter lastModifiedDate;

    private IntegerFilter available;

    public LogisticsCenterCriteria() {
    }

    public LogisticsCenterCriteria(LogisticsCenterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.regionName = other.regionName == null ? null : other.regionName.copy();
        this.logisticsCenterName = other.logisticsCenterName == null ? null : other.logisticsCenterName.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.dimension = other.dimension == null ? null : other.dimension.copy();
        this.creationBy = other.creationBy == null ? null : other.creationBy.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifyTime = other.lastModifyTime == null ? null : other.lastModifyTime.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.available = other.available == null ? null : other.available.copy();
    }

    @Override
    public LogisticsCenterCriteria copy() {
        return new LogisticsCenterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRegionName() {
        return regionName;
    }

    public void setRegionName(StringFilter regionName) {
        this.regionName = regionName;
    }

    public StringFilter getLogisticsCenterName() {
        return logisticsCenterName;
    }

    public void setLogisticsCenterName(StringFilter logisticsCenterName) {
        this.logisticsCenterName = logisticsCenterName;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getDimension() {
        return dimension;
    }

    public void setDimension(StringFilter dimension) {
        this.dimension = dimension;
    }

    public IntegerFilter getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(IntegerFilter creationBy) {
        this.creationBy = creationBy;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public IntegerFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(IntegerFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(InstantFilter lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public IntegerFilter getAvailable() {
        return available;
    }

    public void setAvailable(IntegerFilter available) {
        this.available = available;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LogisticsCenterCriteria that = (LogisticsCenterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(regionName, that.regionName) &&
            Objects.equals(logisticsCenterName, that.logisticsCenterName) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(dimension, that.dimension) &&
            Objects.equals(creationBy, that.creationBy) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifyTime, that.lastModifyTime) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        regionName,
        logisticsCenterName,
        longitude,
        dimension,
        creationBy,
        creationDate,
        lastModifiedBy,
        lastModifyTime,
        lastModifiedDate,
        available
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogisticsCenterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (regionName != null ? "regionName=" + regionName + ", " : "") +
                (logisticsCenterName != null ? "logisticsCenterName=" + logisticsCenterName + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (dimension != null ? "dimension=" + dimension + ", " : "") +
                (creationBy != null ? "creationBy=" + creationBy + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifyTime != null ? "lastModifyTime=" + lastModifyTime + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (available != null ? "available=" + available + ", " : "") +
            "}";
    }

}
