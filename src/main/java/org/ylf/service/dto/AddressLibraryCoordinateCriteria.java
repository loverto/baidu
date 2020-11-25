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
 * Criteria class for the {@link org.ylf.domain.AddressLibraryCoordinate} entity. This class is used
 * in {@link org.ylf.web.rest.AddressLibraryCoordinateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /address-library-coordinates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressLibraryCoordinateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter addressId;

    private StringFilter areaId;

    private StringFilter code;

    private StringFilter name;

    private StringFilter zipCode;

    private StringFilter parentCode;

    private StringFilter addrLevel;

    private IntegerFilter available;

    private IntegerFilter seqNo;

    private InstantFilter createTime;

    private InstantFilter updateTime;

    private IntegerFilter limitLine;

    private StringFilter pinyinPrefix;

    private StringFilter districtLatitudeLongitude;

    public AddressLibraryCoordinateCriteria() {
    }

    public AddressLibraryCoordinateCriteria(AddressLibraryCoordinateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.parentCode = other.parentCode == null ? null : other.parentCode.copy();
        this.addrLevel = other.addrLevel == null ? null : other.addrLevel.copy();
        this.available = other.available == null ? null : other.available.copy();
        this.seqNo = other.seqNo == null ? null : other.seqNo.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.limitLine = other.limitLine == null ? null : other.limitLine.copy();
        this.pinyinPrefix = other.pinyinPrefix == null ? null : other.pinyinPrefix.copy();
        this.districtLatitudeLongitude = other.districtLatitudeLongitude == null ? null : other.districtLatitudeLongitude.copy();
    }

    @Override
    public AddressLibraryCoordinateCriteria copy() {
        return new AddressLibraryCoordinateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(StringFilter addressId) {
        this.addressId = addressId;
    }

    public StringFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(StringFilter areaId) {
        this.areaId = areaId;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
    }

    public StringFilter getParentCode() {
        return parentCode;
    }

    public void setParentCode(StringFilter parentCode) {
        this.parentCode = parentCode;
    }

    public StringFilter getAddrLevel() {
        return addrLevel;
    }

    public void setAddrLevel(StringFilter addrLevel) {
        this.addrLevel = addrLevel;
    }

    public IntegerFilter getAvailable() {
        return available;
    }

    public void setAvailable(IntegerFilter available) {
        this.available = available;
    }

    public IntegerFilter getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(IntegerFilter seqNo) {
        this.seqNo = seqNo;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public IntegerFilter getLimitLine() {
        return limitLine;
    }

    public void setLimitLine(IntegerFilter limitLine) {
        this.limitLine = limitLine;
    }

    public StringFilter getPinyinPrefix() {
        return pinyinPrefix;
    }

    public void setPinyinPrefix(StringFilter pinyinPrefix) {
        this.pinyinPrefix = pinyinPrefix;
    }

    public StringFilter getDistrictLatitudeLongitude() {
        return districtLatitudeLongitude;
    }

    public void setDistrictLatitudeLongitude(StringFilter districtLatitudeLongitude) {
        this.districtLatitudeLongitude = districtLatitudeLongitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressLibraryCoordinateCriteria that = (AddressLibraryCoordinateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(parentCode, that.parentCode) &&
            Objects.equals(addrLevel, that.addrLevel) &&
            Objects.equals(available, that.available) &&
            Objects.equals(seqNo, that.seqNo) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(limitLine, that.limitLine) &&
            Objects.equals(pinyinPrefix, that.pinyinPrefix) &&
            Objects.equals(districtLatitudeLongitude, that.districtLatitudeLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        addressId,
        areaId,
        code,
        name,
        zipCode,
        parentCode,
        addrLevel,
        available,
        seqNo,
        createTime,
        updateTime,
        limitLine,
        pinyinPrefix,
        districtLatitudeLongitude
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressLibraryCoordinateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
                (parentCode != null ? "parentCode=" + parentCode + ", " : "") +
                (addrLevel != null ? "addrLevel=" + addrLevel + ", " : "") +
                (available != null ? "available=" + available + ", " : "") +
                (seqNo != null ? "seqNo=" + seqNo + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (limitLine != null ? "limitLine=" + limitLine + ", " : "") +
                (pinyinPrefix != null ? "pinyinPrefix=" + pinyinPrefix + ", " : "") +
                (districtLatitudeLongitude != null ? "districtLatitudeLongitude=" + districtLatitudeLongitude + ", " : "") +
            "}";
    }

}
