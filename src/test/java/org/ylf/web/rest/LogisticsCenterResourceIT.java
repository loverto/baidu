package org.ylf.web.rest;

import org.ylf.BaiduApp;
import org.ylf.domain.LogisticsCenter;
import org.ylf.repository.LogisticsCenterRepository;
import org.ylf.service.LogisticsCenterService;
import org.ylf.service.dto.LogisticsCenterCriteria;
import org.ylf.service.LogisticsCenterQueryService;

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
 * Integration tests for the {@link LogisticsCenterResource} REST controller.
 */
@SpringBootTest(classes = BaiduApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogisticsCenterResourceIT {

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGISTICS_CENTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGISTICS_CENTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATION_BY = 1;
    private static final Integer UPDATED_CREATION_BY = 2;
    private static final Integer SMALLER_CREATION_BY = 1 - 1;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LAST_MODIFIED_BY = 1;
    private static final Integer UPDATED_LAST_MODIFIED_BY = 2;
    private static final Integer SMALLER_LAST_MODIFIED_BY = 1 - 1;

    private static final Instant DEFAULT_LAST_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AVAILABLE = 1;
    private static final Integer UPDATED_AVAILABLE = 2;
    private static final Integer SMALLER_AVAILABLE = 1 - 1;

    @Autowired
    private LogisticsCenterRepository logisticsCenterRepository;

    @Autowired
    private LogisticsCenterService logisticsCenterService;

    @Autowired
    private LogisticsCenterQueryService logisticsCenterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogisticsCenterMockMvc;

    private LogisticsCenter logisticsCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogisticsCenter createEntity(EntityManager em) {
        LogisticsCenter logisticsCenter = new LogisticsCenter()
            .regionName(DEFAULT_REGION_NAME)
            .logisticsCenterName(DEFAULT_LOGISTICS_CENTER_NAME)
            .longitude(DEFAULT_LONGITUDE)
            .dimension(DEFAULT_DIMENSION)
            .creationBy(DEFAULT_CREATION_BY)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifyTime(DEFAULT_LAST_MODIFY_TIME)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .available(DEFAULT_AVAILABLE);
        return logisticsCenter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogisticsCenter createUpdatedEntity(EntityManager em) {
        LogisticsCenter logisticsCenter = new LogisticsCenter()
            .regionName(UPDATED_REGION_NAME)
            .logisticsCenterName(UPDATED_LOGISTICS_CENTER_NAME)
            .longitude(UPDATED_LONGITUDE)
            .dimension(UPDATED_DIMENSION)
            .creationBy(UPDATED_CREATION_BY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifyTime(UPDATED_LAST_MODIFY_TIME)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .available(UPDATED_AVAILABLE);
        return logisticsCenter;
    }

    @BeforeEach
    public void initTest() {
        logisticsCenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogisticsCenter() throws Exception {
        int databaseSizeBeforeCreate = logisticsCenterRepository.findAll().size();
        // Create the LogisticsCenter
        restLogisticsCenterMockMvc.perform(post("/api/logistics-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsCenter)))
            .andExpect(status().isCreated());

        // Validate the LogisticsCenter in the database
        List<LogisticsCenter> logisticsCenterList = logisticsCenterRepository.findAll();
        assertThat(logisticsCenterList).hasSize(databaseSizeBeforeCreate + 1);
        LogisticsCenter testLogisticsCenter = logisticsCenterList.get(logisticsCenterList.size() - 1);
        assertThat(testLogisticsCenter.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testLogisticsCenter.getLogisticsCenterName()).isEqualTo(DEFAULT_LOGISTICS_CENTER_NAME);
        assertThat(testLogisticsCenter.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLogisticsCenter.getDimension()).isEqualTo(DEFAULT_DIMENSION);
        assertThat(testLogisticsCenter.getCreationBy()).isEqualTo(DEFAULT_CREATION_BY);
        assertThat(testLogisticsCenter.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testLogisticsCenter.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLogisticsCenter.getLastModifyTime()).isEqualTo(DEFAULT_LAST_MODIFY_TIME);
        assertThat(testLogisticsCenter.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testLogisticsCenter.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
    }

    @Test
    @Transactional
    public void createLogisticsCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logisticsCenterRepository.findAll().size();

        // Create the LogisticsCenter with an existing ID
        logisticsCenter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogisticsCenterMockMvc.perform(post("/api/logistics-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsCenter)))
            .andExpect(status().isBadRequest());

        // Validate the LogisticsCenter in the database
        List<LogisticsCenter> logisticsCenterList = logisticsCenterRepository.findAll();
        assertThat(logisticsCenterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLogisticsCenters() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logisticsCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].logisticsCenterName").value(hasItem(DEFAULT_LOGISTICS_CENTER_NAME)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].dimension").value(hasItem(DEFAULT_DIMENSION)))
            .andExpect(jsonPath("$.[*].creationBy").value(hasItem(DEFAULT_CREATION_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifyTime").value(hasItem(DEFAULT_LAST_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)));
    }
    
    @Test
    @Transactional
    public void getLogisticsCenter() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get the logisticsCenter
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers/{id}", logisticsCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logisticsCenter.getId().intValue()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME))
            .andExpect(jsonPath("$.logisticsCenterName").value(DEFAULT_LOGISTICS_CENTER_NAME))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION))
            .andExpect(jsonPath("$.creationBy").value(DEFAULT_CREATION_BY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifyTime").value(DEFAULT_LAST_MODIFY_TIME.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE));
    }


    @Test
    @Transactional
    public void getLogisticsCentersByIdFiltering() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        Long id = logisticsCenter.getId();

        defaultLogisticsCenterShouldBeFound("id.equals=" + id);
        defaultLogisticsCenterShouldNotBeFound("id.notEquals=" + id);

        defaultLogisticsCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogisticsCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultLogisticsCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogisticsCenterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName equals to DEFAULT_REGION_NAME
        defaultLogisticsCenterShouldBeFound("regionName.equals=" + DEFAULT_REGION_NAME);

        // Get all the logisticsCenterList where regionName equals to UPDATED_REGION_NAME
        defaultLogisticsCenterShouldNotBeFound("regionName.equals=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName not equals to DEFAULT_REGION_NAME
        defaultLogisticsCenterShouldNotBeFound("regionName.notEquals=" + DEFAULT_REGION_NAME);

        // Get all the logisticsCenterList where regionName not equals to UPDATED_REGION_NAME
        defaultLogisticsCenterShouldBeFound("regionName.notEquals=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName in DEFAULT_REGION_NAME or UPDATED_REGION_NAME
        defaultLogisticsCenterShouldBeFound("regionName.in=" + DEFAULT_REGION_NAME + "," + UPDATED_REGION_NAME);

        // Get all the logisticsCenterList where regionName equals to UPDATED_REGION_NAME
        defaultLogisticsCenterShouldNotBeFound("regionName.in=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName is not null
        defaultLogisticsCenterShouldBeFound("regionName.specified=true");

        // Get all the logisticsCenterList where regionName is null
        defaultLogisticsCenterShouldNotBeFound("regionName.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName contains DEFAULT_REGION_NAME
        defaultLogisticsCenterShouldBeFound("regionName.contains=" + DEFAULT_REGION_NAME);

        // Get all the logisticsCenterList where regionName contains UPDATED_REGION_NAME
        defaultLogisticsCenterShouldNotBeFound("regionName.contains=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByRegionNameNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where regionName does not contain DEFAULT_REGION_NAME
        defaultLogisticsCenterShouldNotBeFound("regionName.doesNotContain=" + DEFAULT_REGION_NAME);

        // Get all the logisticsCenterList where regionName does not contain UPDATED_REGION_NAME
        defaultLogisticsCenterShouldBeFound("regionName.doesNotContain=" + UPDATED_REGION_NAME);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName equals to DEFAULT_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.equals=" + DEFAULT_LOGISTICS_CENTER_NAME);

        // Get all the logisticsCenterList where logisticsCenterName equals to UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.equals=" + UPDATED_LOGISTICS_CENTER_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName not equals to DEFAULT_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.notEquals=" + DEFAULT_LOGISTICS_CENTER_NAME);

        // Get all the logisticsCenterList where logisticsCenterName not equals to UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.notEquals=" + UPDATED_LOGISTICS_CENTER_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName in DEFAULT_LOGISTICS_CENTER_NAME or UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.in=" + DEFAULT_LOGISTICS_CENTER_NAME + "," + UPDATED_LOGISTICS_CENTER_NAME);

        // Get all the logisticsCenterList where logisticsCenterName equals to UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.in=" + UPDATED_LOGISTICS_CENTER_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName is not null
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.specified=true");

        // Get all the logisticsCenterList where logisticsCenterName is null
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName contains DEFAULT_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.contains=" + DEFAULT_LOGISTICS_CENTER_NAME);

        // Get all the logisticsCenterList where logisticsCenterName contains UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.contains=" + UPDATED_LOGISTICS_CENTER_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLogisticsCenterNameNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where logisticsCenterName does not contain DEFAULT_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldNotBeFound("logisticsCenterName.doesNotContain=" + DEFAULT_LOGISTICS_CENTER_NAME);

        // Get all the logisticsCenterList where logisticsCenterName does not contain UPDATED_LOGISTICS_CENTER_NAME
        defaultLogisticsCenterShouldBeFound("logisticsCenterName.doesNotContain=" + UPDATED_LOGISTICS_CENTER_NAME);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude equals to DEFAULT_LONGITUDE
        defaultLogisticsCenterShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the logisticsCenterList where longitude equals to UPDATED_LONGITUDE
        defaultLogisticsCenterShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude not equals to DEFAULT_LONGITUDE
        defaultLogisticsCenterShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the logisticsCenterList where longitude not equals to UPDATED_LONGITUDE
        defaultLogisticsCenterShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultLogisticsCenterShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the logisticsCenterList where longitude equals to UPDATED_LONGITUDE
        defaultLogisticsCenterShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude is not null
        defaultLogisticsCenterShouldBeFound("longitude.specified=true");

        // Get all the logisticsCenterList where longitude is null
        defaultLogisticsCenterShouldNotBeFound("longitude.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude contains DEFAULT_LONGITUDE
        defaultLogisticsCenterShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the logisticsCenterList where longitude contains UPDATED_LONGITUDE
        defaultLogisticsCenterShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where longitude does not contain DEFAULT_LONGITUDE
        defaultLogisticsCenterShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the logisticsCenterList where longitude does not contain UPDATED_LONGITUDE
        defaultLogisticsCenterShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension equals to DEFAULT_DIMENSION
        defaultLogisticsCenterShouldBeFound("dimension.equals=" + DEFAULT_DIMENSION);

        // Get all the logisticsCenterList where dimension equals to UPDATED_DIMENSION
        defaultLogisticsCenterShouldNotBeFound("dimension.equals=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension not equals to DEFAULT_DIMENSION
        defaultLogisticsCenterShouldNotBeFound("dimension.notEquals=" + DEFAULT_DIMENSION);

        // Get all the logisticsCenterList where dimension not equals to UPDATED_DIMENSION
        defaultLogisticsCenterShouldBeFound("dimension.notEquals=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension in DEFAULT_DIMENSION or UPDATED_DIMENSION
        defaultLogisticsCenterShouldBeFound("dimension.in=" + DEFAULT_DIMENSION + "," + UPDATED_DIMENSION);

        // Get all the logisticsCenterList where dimension equals to UPDATED_DIMENSION
        defaultLogisticsCenterShouldNotBeFound("dimension.in=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension is not null
        defaultLogisticsCenterShouldBeFound("dimension.specified=true");

        // Get all the logisticsCenterList where dimension is null
        defaultLogisticsCenterShouldNotBeFound("dimension.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension contains DEFAULT_DIMENSION
        defaultLogisticsCenterShouldBeFound("dimension.contains=" + DEFAULT_DIMENSION);

        // Get all the logisticsCenterList where dimension contains UPDATED_DIMENSION
        defaultLogisticsCenterShouldNotBeFound("dimension.contains=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByDimensionNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where dimension does not contain DEFAULT_DIMENSION
        defaultLogisticsCenterShouldNotBeFound("dimension.doesNotContain=" + DEFAULT_DIMENSION);

        // Get all the logisticsCenterList where dimension does not contain UPDATED_DIMENSION
        defaultLogisticsCenterShouldBeFound("dimension.doesNotContain=" + UPDATED_DIMENSION);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy equals to DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.equals=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy equals to UPDATED_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.equals=" + UPDATED_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy not equals to DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.notEquals=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy not equals to UPDATED_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.notEquals=" + UPDATED_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy in DEFAULT_CREATION_BY or UPDATED_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.in=" + DEFAULT_CREATION_BY + "," + UPDATED_CREATION_BY);

        // Get all the logisticsCenterList where creationBy equals to UPDATED_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.in=" + UPDATED_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy is not null
        defaultLogisticsCenterShouldBeFound("creationBy.specified=true");

        // Get all the logisticsCenterList where creationBy is null
        defaultLogisticsCenterShouldNotBeFound("creationBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy is greater than or equal to DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.greaterThanOrEqual=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy is greater than or equal to UPDATED_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.greaterThanOrEqual=" + UPDATED_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy is less than or equal to DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.lessThanOrEqual=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy is less than or equal to SMALLER_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.lessThanOrEqual=" + SMALLER_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsLessThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy is less than DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.lessThan=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy is less than UPDATED_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.lessThan=" + UPDATED_CREATION_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationBy is greater than DEFAULT_CREATION_BY
        defaultLogisticsCenterShouldNotBeFound("creationBy.greaterThan=" + DEFAULT_CREATION_BY);

        // Get all the logisticsCenterList where creationBy is greater than SMALLER_CREATION_BY
        defaultLogisticsCenterShouldBeFound("creationBy.greaterThan=" + SMALLER_CREATION_BY);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationDate equals to DEFAULT_CREATION_DATE
        defaultLogisticsCenterShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the logisticsCenterList where creationDate equals to UPDATED_CREATION_DATE
        defaultLogisticsCenterShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationDate not equals to DEFAULT_CREATION_DATE
        defaultLogisticsCenterShouldNotBeFound("creationDate.notEquals=" + DEFAULT_CREATION_DATE);

        // Get all the logisticsCenterList where creationDate not equals to UPDATED_CREATION_DATE
        defaultLogisticsCenterShouldBeFound("creationDate.notEquals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultLogisticsCenterShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the logisticsCenterList where creationDate equals to UPDATED_CREATION_DATE
        defaultLogisticsCenterShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where creationDate is not null
        defaultLogisticsCenterShouldBeFound("creationDate.specified=true");

        // Get all the logisticsCenterList where creationDate is null
        defaultLogisticsCenterShouldNotBeFound("creationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy is not null
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.specified=true");

        // Get all the logisticsCenterList where lastModifiedBy is null
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy is greater than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy is greater than or equal to UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy is less than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy is less than or equal to SMALLER_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy is less than DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.lessThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy is less than UPDATED_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.lessThan=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedBy is greater than DEFAULT_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldNotBeFound("lastModifiedBy.greaterThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the logisticsCenterList where lastModifiedBy is greater than SMALLER_LAST_MODIFIED_BY
        defaultLogisticsCenterShouldBeFound("lastModifiedBy.greaterThan=" + SMALLER_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifyTime equals to DEFAULT_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldBeFound("lastModifyTime.equals=" + DEFAULT_LAST_MODIFY_TIME);

        // Get all the logisticsCenterList where lastModifyTime equals to UPDATED_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldNotBeFound("lastModifyTime.equals=" + UPDATED_LAST_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifyTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifyTime not equals to DEFAULT_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldNotBeFound("lastModifyTime.notEquals=" + DEFAULT_LAST_MODIFY_TIME);

        // Get all the logisticsCenterList where lastModifyTime not equals to UPDATED_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldBeFound("lastModifyTime.notEquals=" + UPDATED_LAST_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifyTime in DEFAULT_LAST_MODIFY_TIME or UPDATED_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldBeFound("lastModifyTime.in=" + DEFAULT_LAST_MODIFY_TIME + "," + UPDATED_LAST_MODIFY_TIME);

        // Get all the logisticsCenterList where lastModifyTime equals to UPDATED_LAST_MODIFY_TIME
        defaultLogisticsCenterShouldNotBeFound("lastModifyTime.in=" + UPDATED_LAST_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifyTime is not null
        defaultLogisticsCenterShouldBeFound("lastModifyTime.specified=true");

        // Get all the logisticsCenterList where lastModifyTime is null
        defaultLogisticsCenterShouldNotBeFound("lastModifyTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the logisticsCenterList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the logisticsCenterList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the logisticsCenterList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultLogisticsCenterShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where lastModifiedDate is not null
        defaultLogisticsCenterShouldBeFound("lastModifiedDate.specified=true");

        // Get all the logisticsCenterList where lastModifiedDate is null
        defaultLogisticsCenterShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available equals to DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.equals=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available equals to UPDATED_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.equals=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available not equals to DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.notEquals=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available not equals to UPDATED_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.notEquals=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available in DEFAULT_AVAILABLE or UPDATED_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.in=" + DEFAULT_AVAILABLE + "," + UPDATED_AVAILABLE);

        // Get all the logisticsCenterList where available equals to UPDATED_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.in=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available is not null
        defaultLogisticsCenterShouldBeFound("available.specified=true");

        // Get all the logisticsCenterList where available is null
        defaultLogisticsCenterShouldNotBeFound("available.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available is greater than or equal to DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.greaterThanOrEqual=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available is greater than or equal to UPDATED_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.greaterThanOrEqual=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available is less than or equal to DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.lessThanOrEqual=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available is less than or equal to SMALLER_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.lessThanOrEqual=" + SMALLER_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsLessThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available is less than DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.lessThan=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available is less than UPDATED_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.lessThan=" + UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllLogisticsCentersByAvailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        logisticsCenterRepository.saveAndFlush(logisticsCenter);

        // Get all the logisticsCenterList where available is greater than DEFAULT_AVAILABLE
        defaultLogisticsCenterShouldNotBeFound("available.greaterThan=" + DEFAULT_AVAILABLE);

        // Get all the logisticsCenterList where available is greater than SMALLER_AVAILABLE
        defaultLogisticsCenterShouldBeFound("available.greaterThan=" + SMALLER_AVAILABLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogisticsCenterShouldBeFound(String filter) throws Exception {
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logisticsCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].logisticsCenterName").value(hasItem(DEFAULT_LOGISTICS_CENTER_NAME)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].dimension").value(hasItem(DEFAULT_DIMENSION)))
            .andExpect(jsonPath("$.[*].creationBy").value(hasItem(DEFAULT_CREATION_BY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifyTime").value(hasItem(DEFAULT_LAST_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)));

        // Check, that the count call also returns 1
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogisticsCenterShouldNotBeFound(String filter) throws Exception {
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLogisticsCenter() throws Exception {
        // Get the logisticsCenter
        restLogisticsCenterMockMvc.perform(get("/api/logistics-centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogisticsCenter() throws Exception {
        // Initialize the database
        logisticsCenterService.save(logisticsCenter);

        int databaseSizeBeforeUpdate = logisticsCenterRepository.findAll().size();

        // Update the logisticsCenter
        LogisticsCenter updatedLogisticsCenter = logisticsCenterRepository.findById(logisticsCenter.getId()).get();
        // Disconnect from session so that the updates on updatedLogisticsCenter are not directly saved in db
        em.detach(updatedLogisticsCenter);
        updatedLogisticsCenter
            .regionName(UPDATED_REGION_NAME)
            .logisticsCenterName(UPDATED_LOGISTICS_CENTER_NAME)
            .longitude(UPDATED_LONGITUDE)
            .dimension(UPDATED_DIMENSION)
            .creationBy(UPDATED_CREATION_BY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifyTime(UPDATED_LAST_MODIFY_TIME)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .available(UPDATED_AVAILABLE);

        restLogisticsCenterMockMvc.perform(put("/api/logistics-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogisticsCenter)))
            .andExpect(status().isOk());

        // Validate the LogisticsCenter in the database
        List<LogisticsCenter> logisticsCenterList = logisticsCenterRepository.findAll();
        assertThat(logisticsCenterList).hasSize(databaseSizeBeforeUpdate);
        LogisticsCenter testLogisticsCenter = logisticsCenterList.get(logisticsCenterList.size() - 1);
        assertThat(testLogisticsCenter.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testLogisticsCenter.getLogisticsCenterName()).isEqualTo(UPDATED_LOGISTICS_CENTER_NAME);
        assertThat(testLogisticsCenter.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLogisticsCenter.getDimension()).isEqualTo(UPDATED_DIMENSION);
        assertThat(testLogisticsCenter.getCreationBy()).isEqualTo(UPDATED_CREATION_BY);
        assertThat(testLogisticsCenter.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testLogisticsCenter.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLogisticsCenter.getLastModifyTime()).isEqualTo(UPDATED_LAST_MODIFY_TIME);
        assertThat(testLogisticsCenter.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testLogisticsCenter.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingLogisticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = logisticsCenterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogisticsCenterMockMvc.perform(put("/api/logistics-centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsCenter)))
            .andExpect(status().isBadRequest());

        // Validate the LogisticsCenter in the database
        List<LogisticsCenter> logisticsCenterList = logisticsCenterRepository.findAll();
        assertThat(logisticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogisticsCenter() throws Exception {
        // Initialize the database
        logisticsCenterService.save(logisticsCenter);

        int databaseSizeBeforeDelete = logisticsCenterRepository.findAll().size();

        // Delete the logisticsCenter
        restLogisticsCenterMockMvc.perform(delete("/api/logistics-centers/{id}", logisticsCenter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogisticsCenter> logisticsCenterList = logisticsCenterRepository.findAll();
        assertThat(logisticsCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
