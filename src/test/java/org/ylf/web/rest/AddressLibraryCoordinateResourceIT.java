package org.ylf.web.rest;

import org.ylf.BaiduApp;
import org.ylf.domain.AddressLibraryCoordinate;
import org.ylf.repository.AddressLibraryCoordinateRepository;
import org.ylf.service.AddressLibraryCoordinateService;
import org.ylf.service.dto.AddressLibraryCoordinateCriteria;
import org.ylf.service.AddressLibraryCoordinateQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AddressLibraryCoordinateResource} REST controller.
 */
@SpringBootTest(classes = BaiduApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressLibraryCoordinateResourceIT {

    private static final String DEFAULT_ADDRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_ID = "AAAAAAAAAA";
    private static final String UPDATED_AREA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_LEVEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVAILABLE = 1;
    private static final Integer UPDATED_AVAILABLE = 2;
    private static final Integer SMALLER_AVAILABLE = 1 - 1;

    private static final Integer DEFAULT_SEQ_NO = 1;
    private static final Integer UPDATED_SEQ_NO = 2;
    private static final Integer SMALLER_SEQ_NO = 1 - 1;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LIMIT_LINE = 1;
    private static final Integer UPDATED_LIMIT_LINE = 2;
    private static final Integer SMALLER_LIMIT_LINE = 1 - 1;

    private static final String DEFAULT_PINYIN_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PINYIN_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_LATITUDE_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_LATITUDE_LONGITUDE = "BBBBBBBBBB";

    @Autowired
    private AddressLibraryCoordinateRepository addressLibraryCoordinateRepository;

    @Autowired
    private AddressLibraryCoordinateService addressLibraryCoordinateService;

    @Autowired
    private AddressLibraryCoordinateQueryService addressLibraryCoordinateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressLibraryCoordinateMockMvc;

    private AddressLibraryCoordinate addressLibraryCoordinate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressLibraryCoordinate createEntity(EntityManager em) {
        AddressLibraryCoordinate addressLibraryCoordinate = new AddressLibraryCoordinate()
            .addressId(DEFAULT_ADDRESS_ID)
            .areaId(DEFAULT_AREA_ID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .zipCode(DEFAULT_ZIP_CODE)
            .parentCode(DEFAULT_PARENT_CODE)
            .addrLevel(DEFAULT_ADDR_LEVEL)
            .available(DEFAULT_AVAILABLE)
            .seqNo(DEFAULT_SEQ_NO)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .limitLine(DEFAULT_LIMIT_LINE)
            .pinyinPrefix(DEFAULT_PINYIN_PREFIX)
            .districtLatitudeLongitude(DEFAULT_DISTRICT_LATITUDE_LONGITUDE);
        return addressLibraryCoordinate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressLibraryCoordinate createUpdatedEntity(EntityManager em) {
        AddressLibraryCoordinate addressLibraryCoordinate = new AddressLibraryCoordinate()
            .addressId(UPDATED_ADDRESS_ID)
            .areaId(UPDATED_AREA_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .addrLevel(UPDATED_ADDR_LEVEL)
            .available(UPDATED_AVAILABLE)
            .seqNo(UPDATED_SEQ_NO)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .limitLine(UPDATED_LIMIT_LINE)
            .pinyinPrefix(UPDATED_PINYIN_PREFIX)
            .districtLatitudeLongitude(UPDATED_DISTRICT_LATITUDE_LONGITUDE);
        return addressLibraryCoordinate;
    }

    @BeforeEach
    public void initTest() {
        addressLibraryCoordinate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressLibraryCoordinate() throws Exception {
        int databaseSizeBeforeCreate = addressLibraryCoordinateRepository.findAll().size();
        // Create the AddressLibraryCoordinate
        restAddressLibraryCoordinateMockMvc.perform(post("/api/address-library-coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressLibraryCoordinate)))
            .andExpect(status().isCreated());

        // Validate the AddressLibraryCoordinate in the database
        List<AddressLibraryCoordinate> addressLibraryCoordinateList = addressLibraryCoordinateRepository.findAll();
        assertThat(addressLibraryCoordinateList).hasSize(databaseSizeBeforeCreate + 1);
        AddressLibraryCoordinate testAddressLibraryCoordinate = addressLibraryCoordinateList.get(addressLibraryCoordinateList.size() - 1);
        assertThat(testAddressLibraryCoordinate.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testAddressLibraryCoordinate.getAreaId()).isEqualTo(DEFAULT_AREA_ID);
        assertThat(testAddressLibraryCoordinate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAddressLibraryCoordinate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddressLibraryCoordinate.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAddressLibraryCoordinate.getParentCode()).isEqualTo(DEFAULT_PARENT_CODE);
        assertThat(testAddressLibraryCoordinate.getAddrLevel()).isEqualTo(DEFAULT_ADDR_LEVEL);
        assertThat(testAddressLibraryCoordinate.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testAddressLibraryCoordinate.getSeqNo()).isEqualTo(DEFAULT_SEQ_NO);
        assertThat(testAddressLibraryCoordinate.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testAddressLibraryCoordinate.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testAddressLibraryCoordinate.getLimitLine()).isEqualTo(DEFAULT_LIMIT_LINE);
        assertThat(testAddressLibraryCoordinate.getPinyinPrefix()).isEqualTo(DEFAULT_PINYIN_PREFIX);
        assertThat(testAddressLibraryCoordinate.getDistrictLatitudeLongitude()).isEqualTo(DEFAULT_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void createAddressLibraryCoordinateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressLibraryCoordinateRepository.findAll().size();

        // Create the AddressLibraryCoordinate with an existing ID
        addressLibraryCoordinate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressLibraryCoordinateMockMvc.perform(post("/api/address-library-coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressLibraryCoordinate)))
            .andExpect(status().isBadRequest());

        // Validate the AddressLibraryCoordinate in the database
        List<AddressLibraryCoordinate> addressLibraryCoordinateList = addressLibraryCoordinateRepository.findAll();
        assertThat(addressLibraryCoordinateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinates() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressLibraryCoordinate.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID)))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(DEFAULT_AREA_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE)))
            .andExpect(jsonPath("$.[*].addrLevel").value(hasItem(DEFAULT_ADDR_LEVEL)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].limitLine").value(hasItem(DEFAULT_LIMIT_LINE)))
            .andExpect(jsonPath("$.[*].pinyinPrefix").value(hasItem(DEFAULT_PINYIN_PREFIX)))
            .andExpect(jsonPath("$.[*].districtLatitudeLongitude").value(hasItem(DEFAULT_DISTRICT_LATITUDE_LONGITUDE)));
    }
    
    @Test
    @Transactional
    public void getAddressLibraryCoordinate() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get the addressLibraryCoordinate
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates/{id}", addressLibraryCoordinate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addressLibraryCoordinate.getId().intValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID))
            .andExpect(jsonPath("$.areaId").value(DEFAULT_AREA_ID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.parentCode").value(DEFAULT_PARENT_CODE))
            .andExpect(jsonPath("$.addrLevel").value(DEFAULT_ADDR_LEVEL))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE))
            .andExpect(jsonPath("$.seqNo").value(DEFAULT_SEQ_NO))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.limitLine").value(DEFAULT_LIMIT_LINE))
            .andExpect(jsonPath("$.pinyinPrefix").value(DEFAULT_PINYIN_PREFIX))
            .andExpect(jsonPath("$.districtLatitudeLongitude").value(DEFAULT_DISTRICT_LATITUDE_LONGITUDE));
    }


    @Test
    @Transactional
    public void getAddressLibraryCoordinatesByIdFiltering() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        Long id = addressLibraryCoordinate.getId();

        defaultAddressLibraryCoordinateShouldBeFound("id.equals=" + id);
        defaultAddressLibraryCoordinateShouldNotBeFound("id.notEquals=" + id);

        defaultAddressLibraryCoordinateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressLibraryCoordinateShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressLibraryCoordinateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressLibraryCoordinateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId equals to DEFAULT_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldBeFound("addressId.equals=" + DEFAULT_ADDRESS_ID);

        // Get all the addressLibraryCoordinateList where addressId equals to UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.equals=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId not equals to DEFAULT_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.notEquals=" + DEFAULT_ADDRESS_ID);

        // Get all the addressLibraryCoordinateList where addressId not equals to UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldBeFound("addressId.notEquals=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId in DEFAULT_ADDRESS_ID or UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldBeFound("addressId.in=" + DEFAULT_ADDRESS_ID + "," + UPDATED_ADDRESS_ID);

        // Get all the addressLibraryCoordinateList where addressId equals to UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.in=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId is not null
        defaultAddressLibraryCoordinateShouldBeFound("addressId.specified=true");

        // Get all the addressLibraryCoordinateList where addressId is null
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId contains DEFAULT_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldBeFound("addressId.contains=" + DEFAULT_ADDRESS_ID);

        // Get all the addressLibraryCoordinateList where addressId contains UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.contains=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddressIdNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addressId does not contain DEFAULT_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("addressId.doesNotContain=" + DEFAULT_ADDRESS_ID);

        // Get all the addressLibraryCoordinateList where addressId does not contain UPDATED_ADDRESS_ID
        defaultAddressLibraryCoordinateShouldBeFound("addressId.doesNotContain=" + UPDATED_ADDRESS_ID);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId equals to DEFAULT_AREA_ID
        defaultAddressLibraryCoordinateShouldBeFound("areaId.equals=" + DEFAULT_AREA_ID);

        // Get all the addressLibraryCoordinateList where areaId equals to UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.equals=" + UPDATED_AREA_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId not equals to DEFAULT_AREA_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.notEquals=" + DEFAULT_AREA_ID);

        // Get all the addressLibraryCoordinateList where areaId not equals to UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldBeFound("areaId.notEquals=" + UPDATED_AREA_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId in DEFAULT_AREA_ID or UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldBeFound("areaId.in=" + DEFAULT_AREA_ID + "," + UPDATED_AREA_ID);

        // Get all the addressLibraryCoordinateList where areaId equals to UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.in=" + UPDATED_AREA_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId is not null
        defaultAddressLibraryCoordinateShouldBeFound("areaId.specified=true");

        // Get all the addressLibraryCoordinateList where areaId is null
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId contains DEFAULT_AREA_ID
        defaultAddressLibraryCoordinateShouldBeFound("areaId.contains=" + DEFAULT_AREA_ID);

        // Get all the addressLibraryCoordinateList where areaId contains UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.contains=" + UPDATED_AREA_ID);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAreaIdNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where areaId does not contain DEFAULT_AREA_ID
        defaultAddressLibraryCoordinateShouldNotBeFound("areaId.doesNotContain=" + DEFAULT_AREA_ID);

        // Get all the addressLibraryCoordinateList where areaId does not contain UPDATED_AREA_ID
        defaultAddressLibraryCoordinateShouldBeFound("areaId.doesNotContain=" + UPDATED_AREA_ID);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code equals to DEFAULT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the addressLibraryCoordinateList where code equals to UPDATED_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code not equals to DEFAULT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the addressLibraryCoordinateList where code not equals to UPDATED_CODE
        defaultAddressLibraryCoordinateShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAddressLibraryCoordinateShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the addressLibraryCoordinateList where code equals to UPDATED_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code is not null
        defaultAddressLibraryCoordinateShouldBeFound("code.specified=true");

        // Get all the addressLibraryCoordinateList where code is null
        defaultAddressLibraryCoordinateShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code contains DEFAULT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the addressLibraryCoordinateList where code contains UPDATED_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where code does not contain DEFAULT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the addressLibraryCoordinateList where code does not contain UPDATED_CODE
        defaultAddressLibraryCoordinateShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name equals to DEFAULT_NAME
        defaultAddressLibraryCoordinateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the addressLibraryCoordinateList where name equals to UPDATED_NAME
        defaultAddressLibraryCoordinateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name not equals to DEFAULT_NAME
        defaultAddressLibraryCoordinateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the addressLibraryCoordinateList where name not equals to UPDATED_NAME
        defaultAddressLibraryCoordinateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAddressLibraryCoordinateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the addressLibraryCoordinateList where name equals to UPDATED_NAME
        defaultAddressLibraryCoordinateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name is not null
        defaultAddressLibraryCoordinateShouldBeFound("name.specified=true");

        // Get all the addressLibraryCoordinateList where name is null
        defaultAddressLibraryCoordinateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name contains DEFAULT_NAME
        defaultAddressLibraryCoordinateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the addressLibraryCoordinateList where name contains UPDATED_NAME
        defaultAddressLibraryCoordinateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where name does not contain DEFAULT_NAME
        defaultAddressLibraryCoordinateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the addressLibraryCoordinateList where name does not contain UPDATED_NAME
        defaultAddressLibraryCoordinateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode equals to DEFAULT_ZIP_CODE
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.equals=" + DEFAULT_ZIP_CODE);

        // Get all the addressLibraryCoordinateList where zipCode equals to UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.equals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode not equals to DEFAULT_ZIP_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.notEquals=" + DEFAULT_ZIP_CODE);

        // Get all the addressLibraryCoordinateList where zipCode not equals to UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.notEquals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode in DEFAULT_ZIP_CODE or UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.in=" + DEFAULT_ZIP_CODE + "," + UPDATED_ZIP_CODE);

        // Get all the addressLibraryCoordinateList where zipCode equals to UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.in=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode is not null
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.specified=true");

        // Get all the addressLibraryCoordinateList where zipCode is null
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode contains DEFAULT_ZIP_CODE
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.contains=" + DEFAULT_ZIP_CODE);

        // Get all the addressLibraryCoordinateList where zipCode contains UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.contains=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByZipCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where zipCode does not contain DEFAULT_ZIP_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("zipCode.doesNotContain=" + DEFAULT_ZIP_CODE);

        // Get all the addressLibraryCoordinateList where zipCode does not contain UPDATED_ZIP_CODE
        defaultAddressLibraryCoordinateShouldBeFound("zipCode.doesNotContain=" + UPDATED_ZIP_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode equals to DEFAULT_PARENT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.equals=" + DEFAULT_PARENT_CODE);

        // Get all the addressLibraryCoordinateList where parentCode equals to UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.equals=" + UPDATED_PARENT_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode not equals to DEFAULT_PARENT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.notEquals=" + DEFAULT_PARENT_CODE);

        // Get all the addressLibraryCoordinateList where parentCode not equals to UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.notEquals=" + UPDATED_PARENT_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode in DEFAULT_PARENT_CODE or UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.in=" + DEFAULT_PARENT_CODE + "," + UPDATED_PARENT_CODE);

        // Get all the addressLibraryCoordinateList where parentCode equals to UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.in=" + UPDATED_PARENT_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode is not null
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.specified=true");

        // Get all the addressLibraryCoordinateList where parentCode is null
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode contains DEFAULT_PARENT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.contains=" + DEFAULT_PARENT_CODE);

        // Get all the addressLibraryCoordinateList where parentCode contains UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.contains=" + UPDATED_PARENT_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByParentCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where parentCode does not contain DEFAULT_PARENT_CODE
        defaultAddressLibraryCoordinateShouldNotBeFound("parentCode.doesNotContain=" + DEFAULT_PARENT_CODE);

        // Get all the addressLibraryCoordinateList where parentCode does not contain UPDATED_PARENT_CODE
        defaultAddressLibraryCoordinateShouldBeFound("parentCode.doesNotContain=" + UPDATED_PARENT_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel equals to DEFAULT_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.equals=" + DEFAULT_ADDR_LEVEL);

        // Get all the addressLibraryCoordinateList where addrLevel equals to UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.equals=" + UPDATED_ADDR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel not equals to DEFAULT_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.notEquals=" + DEFAULT_ADDR_LEVEL);

        // Get all the addressLibraryCoordinateList where addrLevel not equals to UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.notEquals=" + UPDATED_ADDR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel in DEFAULT_ADDR_LEVEL or UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.in=" + DEFAULT_ADDR_LEVEL + "," + UPDATED_ADDR_LEVEL);

        // Get all the addressLibraryCoordinateList where addrLevel equals to UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.in=" + UPDATED_ADDR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel is not null
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.specified=true");

        // Get all the addressLibraryCoordinateList where addrLevel is null
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel contains DEFAULT_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.contains=" + DEFAULT_ADDR_LEVEL);

        // Get all the addressLibraryCoordinateList where addrLevel contains UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.contains=" + UPDATED_ADDR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAddrLevelNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where addrLevel does not contain DEFAULT_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldNotBeFound("addrLevel.doesNotContain=" + DEFAULT_ADDR_LEVEL);

        // Get all the addressLibraryCoordinateList where addrLevel does not contain UPDATED_ADDR_LEVEL
        defaultAddressLibraryCoordinateShouldBeFound("addrLevel.doesNotContain=" + UPDATED_ADDR_LEVEL);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available equals to DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.equals=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available equals to UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.equals=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available not equals to DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.notEquals=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available not equals to UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.notEquals=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available in DEFAULT_AVAILABLE or UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.in=" + DEFAULT_AVAILABLE + "," + UPDATED_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available equals to UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.in=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available is not null
        defaultAddressLibraryCoordinateShouldBeFound("available.specified=true");

        // Get all the addressLibraryCoordinateList where available is null
        defaultAddressLibraryCoordinateShouldNotBeFound("available.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available is greater than or equal to DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.greaterThanOrEqual=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available is greater than or equal to UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.greaterThanOrEqual=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available is less than or equal to DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.lessThanOrEqual=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available is less than or equal to SMALLER_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.lessThanOrEqual=" + SMALLER_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsLessThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available is less than DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.lessThan=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available is less than UPDATED_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.lessThan=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByAvailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where available is greater than DEFAULT_AVAILABLE
        defaultAddressLibraryCoordinateShouldNotBeFound("available.greaterThan=" + DEFAULT_AVAILABLE);

        // Get all the addressLibraryCoordinateList where available is greater than SMALLER_AVAILABLE
        defaultAddressLibraryCoordinateShouldBeFound("available.greaterThan=" + SMALLER_AVAILABLE);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo equals to DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.equals=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo equals to UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.equals=" + UPDATED_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo not equals to DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.notEquals=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo not equals to UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.notEquals=" + UPDATED_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo in DEFAULT_SEQ_NO or UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.in=" + DEFAULT_SEQ_NO + "," + UPDATED_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo equals to UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.in=" + UPDATED_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo is not null
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.specified=true");

        // Get all the addressLibraryCoordinateList where seqNo is null
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo is greater than or equal to DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.greaterThanOrEqual=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo is greater than or equal to UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.greaterThanOrEqual=" + UPDATED_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo is less than or equal to DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.lessThanOrEqual=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo is less than or equal to SMALLER_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.lessThanOrEqual=" + SMALLER_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsLessThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo is less than DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.lessThan=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo is less than UPDATED_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.lessThan=" + UPDATED_SEQ_NO);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesBySeqNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where seqNo is greater than DEFAULT_SEQ_NO
        defaultAddressLibraryCoordinateShouldNotBeFound("seqNo.greaterThan=" + DEFAULT_SEQ_NO);

        // Get all the addressLibraryCoordinateList where seqNo is greater than SMALLER_SEQ_NO
        defaultAddressLibraryCoordinateShouldBeFound("seqNo.greaterThan=" + SMALLER_SEQ_NO);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where createTime equals to DEFAULT_CREATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the addressLibraryCoordinateList where createTime equals to UPDATED_CREATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where createTime not equals to DEFAULT_CREATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the addressLibraryCoordinateList where createTime not equals to UPDATED_CREATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the addressLibraryCoordinateList where createTime equals to UPDATED_CREATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where createTime is not null
        defaultAddressLibraryCoordinateShouldBeFound("createTime.specified=true");

        // Get all the addressLibraryCoordinateList where createTime is null
        defaultAddressLibraryCoordinateShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the addressLibraryCoordinateList where updateTime equals to UPDATED_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the addressLibraryCoordinateList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the addressLibraryCoordinateList where updateTime equals to UPDATED_UPDATE_TIME
        defaultAddressLibraryCoordinateShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where updateTime is not null
        defaultAddressLibraryCoordinateShouldBeFound("updateTime.specified=true");

        // Get all the addressLibraryCoordinateList where updateTime is null
        defaultAddressLibraryCoordinateShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine equals to DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.equals=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine equals to UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.equals=" + UPDATED_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine not equals to DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.notEquals=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine not equals to UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.notEquals=" + UPDATED_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine in DEFAULT_LIMIT_LINE or UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.in=" + DEFAULT_LIMIT_LINE + "," + UPDATED_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine equals to UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.in=" + UPDATED_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine is not null
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.specified=true");

        // Get all the addressLibraryCoordinateList where limitLine is null
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine is greater than or equal to DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.greaterThanOrEqual=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine is greater than or equal to UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.greaterThanOrEqual=" + UPDATED_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine is less than or equal to DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.lessThanOrEqual=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine is less than or equal to SMALLER_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.lessThanOrEqual=" + SMALLER_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsLessThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine is less than DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.lessThan=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine is less than UPDATED_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.lessThan=" + UPDATED_LIMIT_LINE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByLimitLineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where limitLine is greater than DEFAULT_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldNotBeFound("limitLine.greaterThan=" + DEFAULT_LIMIT_LINE);

        // Get all the addressLibraryCoordinateList where limitLine is greater than SMALLER_LIMIT_LINE
        defaultAddressLibraryCoordinateShouldBeFound("limitLine.greaterThan=" + SMALLER_LIMIT_LINE);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix equals to DEFAULT_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.equals=" + DEFAULT_PINYIN_PREFIX);

        // Get all the addressLibraryCoordinateList where pinyinPrefix equals to UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.equals=" + UPDATED_PINYIN_PREFIX);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix not equals to DEFAULT_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.notEquals=" + DEFAULT_PINYIN_PREFIX);

        // Get all the addressLibraryCoordinateList where pinyinPrefix not equals to UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.notEquals=" + UPDATED_PINYIN_PREFIX);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix in DEFAULT_PINYIN_PREFIX or UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.in=" + DEFAULT_PINYIN_PREFIX + "," + UPDATED_PINYIN_PREFIX);

        // Get all the addressLibraryCoordinateList where pinyinPrefix equals to UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.in=" + UPDATED_PINYIN_PREFIX);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix is not null
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.specified=true");

        // Get all the addressLibraryCoordinateList where pinyinPrefix is null
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix contains DEFAULT_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.contains=" + DEFAULT_PINYIN_PREFIX);

        // Get all the addressLibraryCoordinateList where pinyinPrefix contains UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.contains=" + UPDATED_PINYIN_PREFIX);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByPinyinPrefixNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where pinyinPrefix does not contain DEFAULT_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldNotBeFound("pinyinPrefix.doesNotContain=" + DEFAULT_PINYIN_PREFIX);

        // Get all the addressLibraryCoordinateList where pinyinPrefix does not contain UPDATED_PINYIN_PREFIX
        defaultAddressLibraryCoordinateShouldBeFound("pinyinPrefix.doesNotContain=" + UPDATED_PINYIN_PREFIX);
    }


    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude equals to DEFAULT_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.equals=" + DEFAULT_DISTRICT_LATITUDE_LONGITUDE);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude equals to UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.equals=" + UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude not equals to DEFAULT_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.notEquals=" + DEFAULT_DISTRICT_LATITUDE_LONGITUDE);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude not equals to UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.notEquals=" + UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude in DEFAULT_DISTRICT_LATITUDE_LONGITUDE or UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.in=" + DEFAULT_DISTRICT_LATITUDE_LONGITUDE + "," + UPDATED_DISTRICT_LATITUDE_LONGITUDE);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude equals to UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.in=" + UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude is not null
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.specified=true");

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude is null
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude contains DEFAULT_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.contains=" + DEFAULT_DISTRICT_LATITUDE_LONGITUDE);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude contains UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.contains=" + UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressLibraryCoordinatesByDistrictLatitudeLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        addressLibraryCoordinateRepository.saveAndFlush(addressLibraryCoordinate);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude does not contain DEFAULT_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldNotBeFound("districtLatitudeLongitude.doesNotContain=" + DEFAULT_DISTRICT_LATITUDE_LONGITUDE);

        // Get all the addressLibraryCoordinateList where districtLatitudeLongitude does not contain UPDATED_DISTRICT_LATITUDE_LONGITUDE
        defaultAddressLibraryCoordinateShouldBeFound("districtLatitudeLongitude.doesNotContain=" + UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressLibraryCoordinateShouldBeFound(String filter) throws Exception {
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressLibraryCoordinate.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID)))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(DEFAULT_AREA_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].parentCode").value(hasItem(DEFAULT_PARENT_CODE)))
            .andExpect(jsonPath("$.[*].addrLevel").value(hasItem(DEFAULT_ADDR_LEVEL)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].limitLine").value(hasItem(DEFAULT_LIMIT_LINE)))
            .andExpect(jsonPath("$.[*].pinyinPrefix").value(hasItem(DEFAULT_PINYIN_PREFIX)))
            .andExpect(jsonPath("$.[*].districtLatitudeLongitude").value(hasItem(DEFAULT_DISTRICT_LATITUDE_LONGITUDE)));

        // Check, that the count call also returns 1
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressLibraryCoordinateShouldNotBeFound(String filter) throws Exception {
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAddressLibraryCoordinate() throws Exception {
        // Get the addressLibraryCoordinate
        restAddressLibraryCoordinateMockMvc.perform(get("/api/address-library-coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressLibraryCoordinate() throws Exception {
        // Initialize the database
        addressLibraryCoordinateService.save(addressLibraryCoordinate);

        int databaseSizeBeforeUpdate = addressLibraryCoordinateRepository.findAll().size();

        // Update the addressLibraryCoordinate
        AddressLibraryCoordinate updatedAddressLibraryCoordinate = addressLibraryCoordinateRepository.findById(addressLibraryCoordinate.getId()).get();
        // Disconnect from session so that the updates on updatedAddressLibraryCoordinate are not directly saved in db
        em.detach(updatedAddressLibraryCoordinate);
        updatedAddressLibraryCoordinate
            .addressId(UPDATED_ADDRESS_ID)
            .areaId(UPDATED_AREA_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .parentCode(UPDATED_PARENT_CODE)
            .addrLevel(UPDATED_ADDR_LEVEL)
            .available(UPDATED_AVAILABLE)
            .seqNo(UPDATED_SEQ_NO)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .limitLine(UPDATED_LIMIT_LINE)
            .pinyinPrefix(UPDATED_PINYIN_PREFIX)
            .districtLatitudeLongitude(UPDATED_DISTRICT_LATITUDE_LONGITUDE);

        restAddressLibraryCoordinateMockMvc.perform(put("/api/address-library-coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddressLibraryCoordinate)))
            .andExpect(status().isOk());

        // Validate the AddressLibraryCoordinate in the database
        List<AddressLibraryCoordinate> addressLibraryCoordinateList = addressLibraryCoordinateRepository.findAll();
        assertThat(addressLibraryCoordinateList).hasSize(databaseSizeBeforeUpdate);
        AddressLibraryCoordinate testAddressLibraryCoordinate = addressLibraryCoordinateList.get(addressLibraryCoordinateList.size() - 1);
        assertThat(testAddressLibraryCoordinate.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testAddressLibraryCoordinate.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testAddressLibraryCoordinate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAddressLibraryCoordinate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddressLibraryCoordinate.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddressLibraryCoordinate.getParentCode()).isEqualTo(UPDATED_PARENT_CODE);
        assertThat(testAddressLibraryCoordinate.getAddrLevel()).isEqualTo(UPDATED_ADDR_LEVEL);
        assertThat(testAddressLibraryCoordinate.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testAddressLibraryCoordinate.getSeqNo()).isEqualTo(UPDATED_SEQ_NO);
        assertThat(testAddressLibraryCoordinate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testAddressLibraryCoordinate.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testAddressLibraryCoordinate.getLimitLine()).isEqualTo(UPDATED_LIMIT_LINE);
        assertThat(testAddressLibraryCoordinate.getPinyinPrefix()).isEqualTo(UPDATED_PINYIN_PREFIX);
        assertThat(testAddressLibraryCoordinate.getDistrictLatitudeLongitude()).isEqualTo(UPDATED_DISTRICT_LATITUDE_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressLibraryCoordinate() throws Exception {
        int databaseSizeBeforeUpdate = addressLibraryCoordinateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressLibraryCoordinateMockMvc.perform(put("/api/address-library-coordinates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressLibraryCoordinate)))
            .andExpect(status().isBadRequest());

        // Validate the AddressLibraryCoordinate in the database
        List<AddressLibraryCoordinate> addressLibraryCoordinateList = addressLibraryCoordinateRepository.findAll();
        assertThat(addressLibraryCoordinateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressLibraryCoordinate() throws Exception {
        // Initialize the database
        addressLibraryCoordinateService.save(addressLibraryCoordinate);

        int databaseSizeBeforeDelete = addressLibraryCoordinateRepository.findAll().size();

        // Delete the addressLibraryCoordinate
        restAddressLibraryCoordinateMockMvc.perform(delete("/api/address-library-coordinates/{id}", addressLibraryCoordinate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressLibraryCoordinate> addressLibraryCoordinateList = addressLibraryCoordinateRepository.findAll();
        assertThat(addressLibraryCoordinateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
