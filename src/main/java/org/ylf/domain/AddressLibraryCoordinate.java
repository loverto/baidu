package org.ylf.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 带坐标的地址库\n@author ylf
 */
@ApiModel(description = "带坐标的地址库\n@author ylf")
@Entity
@Table(name = "address_library_coordinate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AddressLibraryCoordinate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址编码
     */
    @ApiModelProperty(value = "地址编码")
    @Column(name = "address_id")
    private String addressId;

    /**
     * 地区编码
     */
    @ApiModelProperty(value = "地区编码")
    @Column(name = "area_id")
    private String areaId;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @Column(name = "name")
    private String name;

    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 父级编码
     */
    @ApiModelProperty(value = "父级编码")
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 地址级别
     */
    @ApiModelProperty(value = "地址级别")
    @Column(name = "addr_level")
    private String addrLevel;

    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用")
    @Column(name = "available")
    private Integer available;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    @Column(name = "seq_no")
    private Integer seqNo;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    private Instant updateTime;

    /**
     * 限制行号
     */
    @ApiModelProperty(value = "限制行号")
    @Column(name = "limit_line")
    private Integer limitLine;

    /**
     * 拼音前缀
     */
    @ApiModelProperty(value = "拼音前缀")
    @Column(name = "pinyin_prefix")
    private String pinyinPrefix;

    /**
     * 区县坐标
     */
    @ApiModelProperty(value = "区县坐标")
    @Column(name = "district_latitude_longitude")
    private String districtLatitudeLongitude;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public AddressLibraryCoordinate addressId(String addressId) {
        this.addressId = addressId;
        return this;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAreaId() {
        return areaId;
    }

    public AddressLibraryCoordinate areaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCode() {
        return code;
    }

    public AddressLibraryCoordinate code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AddressLibraryCoordinate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public AddressLibraryCoordinate zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public AddressLibraryCoordinate parentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getAddrLevel() {
        return addrLevel;
    }

    public AddressLibraryCoordinate addrLevel(String addrLevel) {
        this.addrLevel = addrLevel;
        return this;
    }

    public void setAddrLevel(String addrLevel) {
        this.addrLevel = addrLevel;
    }

    public Integer getAvailable() {
        return available;
    }

    public AddressLibraryCoordinate available(Integer available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public AddressLibraryCoordinate seqNo(Integer seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public AddressLibraryCoordinate createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public AddressLibraryCoordinate updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLimitLine() {
        return limitLine;
    }

    public AddressLibraryCoordinate limitLine(Integer limitLine) {
        this.limitLine = limitLine;
        return this;
    }

    public void setLimitLine(Integer limitLine) {
        this.limitLine = limitLine;
    }

    public String getPinyinPrefix() {
        return pinyinPrefix;
    }

    public AddressLibraryCoordinate pinyinPrefix(String pinyinPrefix) {
        this.pinyinPrefix = pinyinPrefix;
        return this;
    }

    public void setPinyinPrefix(String pinyinPrefix) {
        this.pinyinPrefix = pinyinPrefix;
    }

    public String getDistrictLatitudeLongitude() {
        return districtLatitudeLongitude;
    }

    public AddressLibraryCoordinate districtLatitudeLongitude(String districtLatitudeLongitude) {
        this.districtLatitudeLongitude = districtLatitudeLongitude;
        return this;
    }

    public void setDistrictLatitudeLongitude(String districtLatitudeLongitude) {
        this.districtLatitudeLongitude = districtLatitudeLongitude;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressLibraryCoordinate)) {
            return false;
        }
        return id != null && id.equals(((AddressLibraryCoordinate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressLibraryCoordinate{" +
            "id=" + getId() +
            ", addressId='" + getAddressId() + "'" +
            ", areaId='" + getAreaId() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", addrLevel='" + getAddrLevel() + "'" +
            ", available=" + getAvailable() +
            ", seqNo=" + getSeqNo() +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", limitLine=" + getLimitLine() +
            ", pinyinPrefix='" + getPinyinPrefix() + "'" +
            ", districtLatitudeLongitude='" + getDistrictLatitudeLongitude() + "'" +
            "}";
    }
}
