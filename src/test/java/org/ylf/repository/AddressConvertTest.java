package org.ylf.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.io.FileUtils;
import org.ylf.BaiduApp;
import org.ylf.domain.AddressLibraryCoordinate;
import org.ylf.domain.LogisticsCenter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ylf
 * @version 1.0.0
 * @description //TODO
 * @date 2020-11-20 15:17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaiduApp.class)
public class AddressConvertTest {

    public static final String GBK = "GBK";
    public static final String JINGNAN_LOGISTICS_CENTER = "京南物流中心";
    public static final String TIANJIN_LOGISTICS_CENTER = "天津物流中心";

    public RestTemplate restTemplate() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
            .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));
        List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        //不加会出现异常
        //Could not extract response: no suitable HttpMessageConverter found for response type [class ]

        MediaType TEXT_JAVASCRIPT = new MediaType("text", "javascript", Charset.forName("UTF-8"));
        MediaType[] mediaTypes = new MediaType[]{
            MediaType.APPLICATION_OCTET_STREAM,
            MediaType.APPLICATION_JSON,
            MediaType.TEXT_HTML,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            MediaType.APPLICATION_ATOM_XML,
            MediaType.APPLICATION_FORM_URLENCODED,
            TEXT_JAVASCRIPT,
            MediaType.APPLICATION_PDF
        };


        converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));

        try {
            //通过反射设置MessageConverters
            Field field = restTemplate.getClass().getDeclaredField("messageConverters");

            field.setAccessible(true);

            List<HttpMessageConverter<?>> orgConverterList = (List<HttpMessageConverter<?>>) field.get(restTemplate);

            Optional<HttpMessageConverter<?>> opConverter = orgConverterList.stream()
                .filter(x -> x.getClass().getName().equalsIgnoreCase(MappingJackson2HttpMessageConverter.class
                    .getName()))
                .findFirst();

            if (opConverter.isPresent() == false) {
                return restTemplate;
            }

            messageConverters.add(converter);//添加MappingJackson2HttpMessageConverter

            //添加原有的剩余的HttpMessageConverter
            List<HttpMessageConverter<?>> leftConverters = orgConverterList.stream()
                .filter(x -> x.getClass().getName().equalsIgnoreCase(MappingJackson2HttpMessageConverter.class
                    .getName()) == false)
                .collect(Collectors.toList());

            messageConverters.addAll(leftConverters);

            System.out.println(String.format("【HttpMessageConverter】原有数量：%s，重新构造后数量：%s"
                , orgConverterList.size(), messageConverters.size()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        restTemplate.setMessageConverters(messageConverters);


        return restTemplate;
    }

    private RestTemplate restTemplate = restTemplate();

    @Autowired
    private AddressLibraryCoordinateRepository addressLibraryCoordinateRepository;


    @Autowired
    private LogisticsCenterRepository logisticsCenterRepository;


    //    String url = "http://api.map.baidu.com/geocoding/v3/?address=%s&ak=%s&output=json";
    String getCoordinatesUrl = "http://api.map.baidu.com/place/v2/search?query=%s&region=%s&output=json&ak=%s";

    String routePlanUrl = "http://api.map.baidu.com/logistics_direction/v1/truck?origin=%s&destination=%s&ak=%s&sn=%s";
    String routePlanUrl2 = "http://api.map.baidu.com/logistics_direction/v1/truck?%s";
//    String routePlanUrl = "http://api.map.baidu.com/logistics_direction/v1/truck?origin=113.562002,22.207363&destination=116.422469,39.723691&ak=RtxqXIRpzGIT0gdhmuZtQ26xVBnAcFl2";

    String uri = "/logistics_direction/v1/truck?%s%s";

    String ak = "RtxqXIRpzGIT0gdhmuZtQ26xVBnAcFl2";
    String ak2 = "SV3ReuvSTiLfHfh0RGArRahFxiX82CWg";

    String sk = "byFurSCzBGppb0GI7GWIyf6E0moyEfIL";

//    private final static String sk = "byFurSCzBGppb0GI7GWIyf6E0moyEfIL";
//
//    private final static String ak = "SV3ReuvSTiLfHfh0RGArRahFxiX82CWg";

//    String addressList = "辽宁省抚顺市抚顺县";

    private Logger logger = LoggerFactory.getLogger(AddressConvertTest.class);


    public static class UserAgentInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            // 这种方案我没有实现成功，所以用的自定义字段
            // headers.add(HttpHeaders.USER_AGENT, "your agent");
            headers.add("Referer", "fsyuncai.com");
            return execution.execute(request, body);
        }
    }


    // 对Map内所有value作utf8编码，拼接返回结果
    public String toQueryString(Map<?, ?> data)
        throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                    .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Test
    public void testTrunk() throws Exception {
        String origin = "29.866288,118.421459";
        String location = "39.723691,116.422469";
        String encode = "";
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("origin", origin);
        paramsMap.put("destination", location);
        paramsMap.put("ak", ak2);
        String s = toQueryString(paramsMap);

        logger.info("query string:{}", s);
        encode = String.format(uri, s, sk);
        logger.info("url:{}", encode);
        String encode1 = URLEncoder.encode(encode, "UTF-8");
        logger.info("url encoding :{}", encode1);
        String sn = MD5(encode1);
        logger.error("sn:{}", sn);

        paramsMap.put("sn", sn);
        String s1 = toQueryString(paramsMap);
        String format = String.format(routePlanUrl, origin, location, ak2, sn);
        String format1 = String.format(routePlanUrl2, s1);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(format1);
        URI uri = uriComponentsBuilder.build(true).toUri();

        logger.info("url:{}", format);
        BaiduTrunkRouterResponse forObject = restTemplate.getForObject(uri, BaiduTrunkRouterResponse.class);
        double distance = forObject.getResult().getRoutes().get(0).getDistance();
        logger.info(forObject.toString());

    }


    @Test
    public void testAddressLibrary() {
        AddressLibraryCoordinate addressLibraryCoordinate = new AddressLibraryCoordinate();
        addressLibraryCoordinate.addrLevel("103");
        addressLibraryCoordinate.available(100);
        Example<AddressLibraryCoordinate> of = Example.of(addressLibraryCoordinate);


        List<AddressLibraryCoordinate> addressLibraries = addressLibraryCoordinateRepository.findAll(of);
        logger.info("数量:{}", addressLibraries.size());
    }

    @Test
    public void getCoordinate() throws Exception {

        List<String> addressWithLatitudeAndLongitude = getAddressWithLatitudeAndLongitude();
        logger.info("包含经纬度数量:{}", addressWithLatitudeAndLongitude.size());

        List<LogisticsCenter> logisticsCenters = getLogisticsCenters();

        List<String> logisticsCenterAndDistrictsCoordinateResult = getLogisticsCenterAndDistrictsCoordinateResult(addressWithLatitudeAndLongitude, logisticsCenters);

        List<String> logisticsCentersAndDistrictsResult = getLogisticsCentersAndDistrictsResult(addressWithLatitudeAndLongitude, logisticsCenters, logisticsCenterAndDistrictsCoordinateResult);
        logisticsCentersAndDistrictsResult.stream().forEach(System.out::println);

        // 不在物流中心的省份
        List<String> noneLogisticsCenter = getNoneLogisticsCenter(addressWithLatitudeAndLongitude, logisticsCenterAndDistrictsCoordinateResult);

        List<String> nonologisticsCentersAndDistrictsResult = getNonologisticsCentersAndDistrictsResult(logisticsCenters, noneLogisticsCenter);

        nonologisticsCentersAndDistrictsResult.stream().forEach(System.out::println);

    }

    /**
     * 不在物流中心的省份
     *
     * @param addressWithLatitudeAndLongitude 包含经纬度的地址信息
     * @param allLogisticsCenter              所有物流中心
     * @return
     */
    @NotNull
    private List<String> getNoneLogisticsCenter(List<String> addressWithLatitudeAndLongitude, List<String> allLogisticsCenter) {
        List<String> noneLogisticsCenter = addressWithLatitudeAndLongitude.stream().filter(address -> !allLogisticsCenter.contains(address)).collect(Collectors.toList());
        return noneLogisticsCenter;
    }

    /**
     * 获取带有物流中心的省份及区县
     * <p>
     * 通过省份和物流中心名称匹配
     *
     * @param addressWithLatitudeAndLongitude 包含经纬度信息的区县
     * @param logisticsCenters                物流中心
     * @return
     */
    private List<String> getLogisticsCenterAndDistrictsCoordinateResult(List<String> addressWithLatitudeAndLongitude, List<LogisticsCenter> logisticsCenters) {
        // 获取有物流中心的数据
        List<String> logisticsCenterAndDistrictsCoordinateResult = addressWithLatitudeAndLongitude.stream().filter(address -> {
            String[] split = address.split("-");
            String provinceName = split[0].split(",")[3];
            return logisticsCenters.stream().map(logisticsCenter -> logisticsCenter.getRegionName()).collect(Collectors.toList()).stream().anyMatch(logisticsCenterName -> logisticsCenterName.contains(provinceName));
        }).collect(Collectors.toList());
        return logisticsCenterAndDistrictsCoordinateResult;
    }

    /**
     * 获取物流中心
     *
     * @return
     */
    @NotNull
    private List<LogisticsCenter> getLogisticsCenters() {
        LogisticsCenter center = new LogisticsCenter();
        center.available(1);
        Example<LogisticsCenter> logisticsCenterExample = Example.of(center);
        List<LogisticsCenter> logisticsCenters = logisticsCenterRepository.findAll(logisticsCenterExample);
        logger.info("物流中心数量：{}", logisticsCenters.size());
        return logisticsCenters;
    }

    @Test
    public void testFile() throws IOException {
        File nonFile = new File("nonlogisticsCenters.csv");
        String readFileToString = FileUtils.readFileToString(nonFile, GBK);
        String[] strings = readFileToString.split("\n");
        List<String> coordinates = Arrays.stream(strings).parallel().map(add -> add.split(",")[1] + "," + add.split(",")[2]).collect(Collectors.toList());
        logger.info("数量:{}", coordinates.size());
    }

    /**
     * 获取不包含物流中心的区县与物流中心的距离
     *
     * @param logisticsCenters    物流中心
     * @param noneLogisticsCenter 包含物流中心的区县包含坐标
     * @return
     */
    private List<String> getNonologisticsCentersAndDistrictsResult(List<LogisticsCenter> logisticsCenters, List<String> noneLogisticsCenter) throws IOException {
        logger.info("无物流中心的区县：{}", noneLogisticsCenter.size());
        List<String> districtsAndCountiesThatNeedPlanning = noneLogisticsCenter;
        File nonFile = new File("nonlogisticsCenters.csv");
        File error = new File("5003.csv");
        List<String> nonologisticsCentersAndDistrictsResult = new ArrayList<>();
        districtsAndCountiesThatNeedPlanning = getDistrictsAndCountiesThatNeedPlanning(noneLogisticsCenter, districtsAndCountiesThatNeedPlanning, nonFile);
        districtsAndCountiesThatNeedPlanning.stream().forEach(address -> {
                String[] split = address.split("-");
                String addr = split[0];
                String districtCoordinate = split[1];
                String districtName = split[2];
                List<String> distanceBetweenDistrictAndLogisticsCenter = getDistanceBetweenDistrictAndLogisticsCenter(logisticsCenters, districtCoordinate);
                Optional<String> shortestDistance = getTheMinimum(distanceBetweenDistrictAndLogisticsCenter);
                Optional<String> secondShortDistance = getSecondShortDistance(distanceBetweenDistrictAndLogisticsCenter, shortestDistance);
                Optional<String> jingnanLogisticsCenter = getLogisticsCenterByName(distanceBetweenDistrictAndLogisticsCenter, JINGNAN_LOGISTICS_CENTER);
                Optional<String> tianjinLogisticsCenter = getLogisticsCenterByName(distanceBetweenDistrictAndLogisticsCenter, TIANJIN_LOGISTICS_CENTER);
                String cityName = addr.split(",")[0];
                String provinceName = addr.split(",")[3];
                if (shortestDistance.isPresent() && secondShortDistance.isPresent() && jingnanLogisticsCenter.isPresent() && tianjinLogisticsCenter.isPresent()) {
                    String shortestDistanceLogisticsCenterName = shortestDistance.orElse(" -").split("-")[0];
                    String shortestDistanceLogisticsCenter = shortestDistance.orElse(" -0").split("-")[1];
                    String secondShortDistanceLogisticsCenterName = secondShortDistance.orElse(" -").split("-")[0];
                    String secondShortDistanceLogisticsCenter = secondShortDistance.orElse("-0").split("-")[1];
                    String jingnanDistanceLogisticsCenter = jingnanLogisticsCenter.orElse(" -0").split("-")[1];
                    String tianjinDistanceLogisticsCenter = tianjinLogisticsCenter.orElse(" -0").split("-")[1];
                    String returnResult2 = districtName + "," + districtCoordinate + "," + cityName + "," + provinceName + "," + shortestDistanceLogisticsCenterName + "," + shortestDistanceLogisticsCenter + "公里" + "," + secondShortDistanceLogisticsCenterName + "," + secondShortDistanceLogisticsCenter + "公里" + "," + jingnanDistanceLogisticsCenter + "公里" + "," + tianjinDistanceLogisticsCenter + "公里";
                    doNotRecordDataRepeatedly(nonFile,returnResult2);
                    nonologisticsCentersAndDistrictsResult.add(returnResult2);
                } else {
                    doNotRecordDataRepeatedly(error,address);
                    logger.warn("有空值：{}", address);
                }
            }
        );
        return nonologisticsCentersAndDistrictsResult;
    }

    /**
     * 通过物流中心名称获取物流中心
     * @param distanceBetweenDistrictAndLogisticsCenter 物流中心及区县距离集合
     * @param logisticsCenterName 物流中心名称
     * @return 物流中心-距离-物流中心坐标
     */
    @NotNull
    private Optional<String> getLogisticsCenterByName(List<String> distanceBetweenDistrictAndLogisticsCenter, String logisticsCenterName) {
        return distanceBetweenDistrictAndLogisticsCenter.stream().filter(logisticsCenter -> logisticsCenter.contains(logisticsCenterName)).findFirst();
    }

    /**
     * 获取除了传入参数之外的最小值
     * @param distanceBetweenDistrictAndLogisticsCenter
     * @param shortestDistance
     * @return 物流中心-距离-物流中心坐标
     */
    @NotNull
    private Optional<String> getSecondShortDistance(List<String> distanceBetweenDistrictAndLogisticsCenter, Optional<String> shortestDistance) {
        List<String> excludeSpecifiedValue = distanceBetweenDistrictAndLogisticsCenter.stream().filter(rr -> !rr.equals(shortestDistance.orElse(""))).collect(Collectors.toList());
        Optional<String> secondShortDistance = getTheMinimum(excludeSpecifiedValue);
        return secondShortDistance;
    }

    /**
     * 获取最小值
     * @param distanceBetweenDistrictAndLogisticsCenter
     * @return 物流中心-距离-物流中心坐标
     */
    @NotNull
    private Optional<String> getTheMinimum(List<String> distanceBetweenDistrictAndLogisticsCenter) {
        Optional<String> shortestDistance = distanceBetweenDistrictAndLogisticsCenter.stream().min((u1, u2) -> {
            Double aDouble = Double.valueOf(u1.split("-")[1]);
            Double bDouble = Double.valueOf(u2.split("-")[1]);
            return aDouble.compareTo(bDouble);
        });
        return shortestDistance;
    }

    /**
     * 获取区县与各个物流中心的距离
     *
     * 优化点: 把结果记录存储到本地,方便下次分析
     *
     * @param logisticsCenters 物流中心
     * @param districtCoordinate 区县坐标
     * @return 物流中心-距离-物流中心坐标
     */
    @NotNull
    private List<String> getDistanceBetweenDistrictAndLogisticsCenter(List<LogisticsCenter> logisticsCenters, String districtCoordinate) {
        List<String> distanceBetweenDistrictAndLogisticsCenter = new ArrayList<>();
        // 计算各物流中心的距离
        logisticsCenters.stream().forEach(logisticsCenter -> {
            String longitude = logisticsCenter.getLongitude();
            //latitude and longitude
            String latitude = logisticsCenter.getDimension();
            String logisticsCenterCoordinate = latitude + "," + longitude;
            BaiduTrunkRouterResponse forObject = getBaiduTrunkRouterResponse(logisticsCenterCoordinate, districtCoordinate);
            Result result = forObject.getResult();
            if (Objects.nonNull(forObject) && Objects.nonNull(result) && Objects.nonNull(result.getRoutes()) && result.getRoutes().size() > 0) {
                double distance = result.getRoutes().get(0).getDistance();
                double kmDistance = distance / 1000;
                String returnResult = logisticsCenter.getLogisticsCenterName() + "-" + kmDistance + "-" + logisticsCenterCoordinate;
                distanceBetweenDistrictAndLogisticsCenter.add(returnResult);
            } else {
                logger.debug("记录为空了:{}",logisticsCenterCoordinate);
            }
        });
        return distanceBetweenDistrictAndLogisticsCenter;
    }

    /**
     * 获取没有写过的数据 通过坐标判断唯一性
     *
     * @param noneLogisticsCenter                  没有物流中心的区县
     * @param districtsAndCountiesThatNeedPlanning 需要规划的区县
     * @param nonFile                              存储规划过的数据
     * @return
     * @throws IOException
     */
    @NotNull
    private List<String> getDistrictsAndCountiesThatNeedPlanning(List<String> noneLogisticsCenter, List<String> districtsAndCountiesThatNeedPlanning, File nonFile) throws IOException {
        if (nonFile.exists()) {
            String readFileToString = FileUtils.readFileToString(nonFile, GBK);
            // 东乡族自治县,35.669328,103.395611,临夏回族自治州,甘肃,陕西物流中心,673.053公里,四川物流中心,1002.582公里,京南物流中心,1543.77公里,天津物流中心,1600.818公里
            // 区县 维度 经度 城市 省 最近物流中心 最近距离 二近物流中心 二近距离 京南物流中心 距离 天津物流中心 距离
            String[] strings = readFileToString.split("\n");
            List<String> coordinates = Arrays.stream(strings).parallel().map(add -> {
                String latitude = add.split(",")[1];
                String longitude = add.split(",")[2];
                return latitude + "," + longitude;
            }).collect(Collectors.toList());
            districtsAndCountiesThatNeedPlanning = noneLogisticsCenter.stream().filter(aa -> !coordinates.contains(aa.split("-")[1])).collect(Collectors.toList());
        }
        // 计算非物流中心覆盖区域
        logger.info("需要请求的条数:{}", districtsAndCountiesThatNeedPlanning.size());
        return districtsAndCountiesThatNeedPlanning;
    }

    /**
     * 按规则计算物流中心对应的路线及结果
     *
     * @param addressWithLatitudeAndLongitude 包含
     * @param logisticsCenters
     * @param allLogisticsCenter
     * @return
     */
    private List<String> getLogisticsCentersAndDistrictsResult(List<String> addressWithLatitudeAndLongitude, List<LogisticsCenter> logisticsCenters, List<String> allLogisticsCenter) {
        logger.info("有物流中心的区县：{}", allLogisticsCenter.size());
        List<String> logisticsCentersAndDistrictsResult = new ArrayList<>();
        File file = new File("logisticsCenters.csv");
        logisticsCenters.stream().forEach(logisticsCenter -> {
            String longitude = logisticsCenter.getLongitude();
            String latitude = logisticsCenter.getDimension();
            String logisticsCenterCoordinate = latitude + "," + longitude;
            // 过滤出本中心仓对应的省份
            String logisticsCenterName = logisticsCenter.getLogisticsCenterName();
            List<String> dataCorrespondingToThisArea = getDataCorrespondingToThisArea(addressWithLatitudeAndLongitude, logisticsCenter);
            // 取出其他物流中心，排除掉本物流中心
            List<LogisticsCenter> otherLogisticsCenters = getOtherLogisticsCenters(logisticsCenters, logisticsCenter);
            dataCorrespondingToThisArea.stream().forEach(address -> {
                String[] split = address.split("-");
                String addr = split[0];
                String districtCoordinate = split[1];
                String districtName = split[2];
                String cityName = addr.split(",")[0];
                BaiduTrunkRouterResponse forObject = getBaiduTrunkRouterResponse(logisticsCenterCoordinate, districtCoordinate);
                Result result = forObject.getResult();
                if (Objects.nonNull(forObject) && Objects.nonNull(result) && Objects.nonNull(result.getRoutes()) && result.getRoutes().size() > 0) {
                    String fullRegionalCenters = getFullRegionalCenters(file, logisticsCenterName, otherLogisticsCenters, cityName, districtCoordinate, districtName, result);
                    logisticsCentersAndDistrictsResult.add(fullRegionalCenters);
                } else {
                    logger.warn("路径信息未取到，{}", address);
                }
            });
        });
        return logisticsCentersAndDistrictsResult;
    }

    /**
     * 获取其他物流中心
     * 这个地方可以用 List.remove来处理,但是考虑到集合变动,所以这里采用了Stream的方式来处理
     * @param logisticsCenters 物流中心
     * @param logisticsCenter 要排除的物流中心
     * @return
     */
    @NotNull
    private List<LogisticsCenter> getOtherLogisticsCenters(List<LogisticsCenter> logisticsCenters, LogisticsCenter logisticsCenter) {
        List<LogisticsCenter> otherLogisticsCenters = logisticsCenters.stream().filter(logisticsCenter1 -> !logisticsCenter1.getRegionName().equals(logisticsCenter.getRegionName())
        ).collect(Collectors.toList());
        return otherLogisticsCenters;
    }

    /**
     * 获取符合指定规则的数据
     *
     * @param file
     * @param logisticsCenterName   物流中心名称
     * @param otherLogisticsCenters 其他物流中心
     * @param cityName              城市名称
     * @param districtCoordinate    区县坐标
     * @param districtName          区县名称
     * @param result
     * @return 区县 经度 纬度 地市 新规则中心仓 所属中心仓 与中心仓距离 临近中心仓名称 临近中心仓距离
     */
    @NotNull
    private String getFullRegionalCenters(File file, String logisticsCenterName, List<LogisticsCenter> otherLogisticsCenters, String cityName, String districtCoordinate, String districtName, Result result) {
        double distance = result.getRoutes().get(0).getDistance();
        double v = distance / 1000;
        String fullRegionalCenters = "";
        String shortestDistance = getTheMinimum(otherLogisticsCenters, districtCoordinate);
        Double v2 = Double.valueOf(shortestDistance.split("-")[1]);
        String otherLogisticsCenterName = shortestDistance.split("-")[0];
        //区县 经度 纬度 地市 新规则中心仓 所属中心仓 与中心仓距离 临近中心仓名称 临近中心仓距离
//        String regionalCenterTemplate = "%s,%s,%s,%s,%s,%s公里,%s,%s公里";
//        String regionalCenterTemplate2 = "%s,%s,%s,%s,%s,%s公里,%s,%s公里,%s公里,%s公里";
//        String.format(regionalCenterTemplate,districtName,districtCoordinate,cityName,logisticsCenterName,logisticsCenterName,v,otherLogisticsCenterName,v2);
        String regionalCenter = districtName + "," + districtCoordinate + "," + cityName + "," + logisticsCenterName + "," + logisticsCenterName + "," + v + "公里" + "," + otherLogisticsCenterName + "," + v2 + "公里";
        String otherRegionalCenters = districtName + "," + districtCoordinate + "," + cityName + "," + otherLogisticsCenterName + "," + logisticsCenterName + "," + v + "公里" + "," + otherLogisticsCenterName + "," + v2 + "公里";
        //String returnResult2 = districtName + "," + districtCoordinate + "," + cityName + "," + provinceName + "," + shortestDistanceLogisticsCenterName + "," + shortestDistanceLogisticsCenter + "公里" + "," + secondShortDistanceLogisticsCenterName + "," + secondShortDistanceLogisticsCenter + "公里" + "," + jingnanDistanceLogisticsCenter + "公里" + "," + tianjinDistanceLogisticsCenter + "公里"
        double distanceDifference = v - v2;

        if ((v < 300 && v >= 150 && v2 < 150 && distanceDifference > 50) || v > 300 && v2 < 300 && !(distanceDifference <= 100 && v / v2 <= 2)) {
            fullRegionalCenters = otherRegionalCenters;
        } else {
            fullRegionalCenters = regionalCenter;
        }
        doNotRecordDataRepeatedly(file, fullRegionalCenters);
        logger.info("结果：{}", fullRegionalCenters);
        return fullRegionalCenters;
    }

    /**
     * 不重复记录数据
     * @param file 文件
     * @param rowInfo 一行信息
     * @throws IOException
     */
    private void doNotRecordDataRepeatedly(File file, String rowInfo) {
        try {
            if (!file.exists()||(file.exists()&&!FileUtils.readFileToString(file, GBK).contains(rowInfo))){
                FileUtils.writeStringToFile(file, rowInfo + "\n", GBK, true);
            }else {
                logger.debug("记录没有写入:{}",rowInfo);
            }
        } catch (IOException e) {
            logger.error("文件写入失败：{}", e.getMessage());
        }

    }

    /**
     * 过滤出本中心仓对应的省份的区县
     *
     * @param addressWithLatitudeAndLongitude 包含坐标的地址信息
     * @param logisticsCenter                 物流中心
     * @return
     */
    private List<String> getDataCorrespondingToThisArea(List<String> addressWithLatitudeAndLongitude, LogisticsCenter logisticsCenter) {
        String regionName = logisticsCenter.getRegionName();
        List<String> dataCorrespondingToThisArea = addressWithLatitudeAndLongitude.stream().filter(address -> {
            String[] split = address.split("-");
            String provinceName = split[0].split(",")[3];
            return regionName.contains(provinceName);
        }).collect(Collectors.toList());
        return dataCorrespondingToThisArea;
    }

    /**
     * 获取指定坐标集合与指定的坐标间的最短距离
     *
     * @param otherLogisticsCenters 物流中心集合
     * @param destinationCoordinate 单个坐标 格式 "维度,经度"
     * @return
     */
    @NotNull
    private String getTheMinimum(List<LogisticsCenter> otherLogisticsCenters, String destinationCoordinate) {
        List<String> dis = new ArrayList<>();
        otherLogisticsCenters.parallelStream().forEach(otherLogisticsCenter -> {
            String longitude = otherLogisticsCenter.getLongitude();
            String latitude = otherLogisticsCenter.getDimension();
            String logisticsCenterName = otherLogisticsCenter.getLogisticsCenterName();
            String logisticsCenterCoordinate = latitude + "," + longitude;
            BaiduTrunkRouterResponse baiduTrunkRouterResponse = getBaiduTrunkRouterResponse(logisticsCenterCoordinate, destinationCoordinate);
            Result result = baiduTrunkRouterResponse.getResult();
            if (Objects.nonNull(baiduTrunkRouterResponse) && Objects.nonNull(result) && Objects.nonNull(result.getRoutes()) && result.getRoutes().size() > 0) {
                double distance2 = result.getRoutes().get(0).getDistance();
                double v2 = distance2 / 1000;
                String returnResult = logisticsCenterName + "-" + v2 + "-" + logisticsCenterCoordinate;
                dis.add(returnResult);

            }
        });
        return dis.stream().min((u1, u2) -> {
            Double aDouble = Double.valueOf(u1.split("-")[1]);
            Double bDouble = Double.valueOf(u2.split("-")[1]);
            return aDouble.compareTo(bDouble);

        }).get();
    }

    @Test
    public void testGetAddressWithLatitudeAndLongtitude() {
        List<String> addressWithLatitudeAndLongitude = this.getAddressWithLatitudeAndLongitude();
        logger.info("数量:{}", addressWithLatitudeAndLongitude.size());

    }

    /**
     * 获取地址及其坐标
     *
     * @return
     */
    @NotNull
    private List<String> getAddressWithLatitudeAndLongitude() {
        AddressLibraryCoordinate addressLibraryCoordinate = new AddressLibraryCoordinate();
        addressLibraryCoordinate.addrLevel("103");
        addressLibraryCoordinate.available(100);
        Example<AddressLibraryCoordinate> of = Example.of(addressLibraryCoordinate);
        List<AddressLibraryCoordinate> addressLibraries = addressLibraryCoordinateRepository.findAll(of);
        List<AddressLibraryCoordinate> addressLibraryCoordinates = addressLibraryCoordinateRepository.findAll();

        List<String> addressWithLatitudeAndLongitude = new ArrayList<>();

        // 处理数据
        addressLibraries.stream().forEach(districtAddress -> {
            String cityCode = districtAddress.getParentCode();
            String districtName = districtAddress.getName();
            AddressLibraryCoordinate cityAddress = getCityAddress(addressLibraryCoordinates, cityCode);
            String cityName = cityAddress.getName();
            String provinceCode = cityAddress.getParentCode();
            AddressLibraryCoordinate provinceAddress = getCityAddress(addressLibraryCoordinates, provinceCode);
            String provinceName = provinceAddress.getName();
            String fullAddress = provinceName + cityName + districtName;
            provinceName = provinceName.replaceAll("市", "").replace("省", "");
            String address = cityName + "," + fullAddress + "," + districtName + "," + provinceName;

            String getRegionCoordinatesUrl = String.format(getCoordinatesUrl, fullAddress, cityName, ak);
            logger.debug("get region coordinate url:{}",getRegionCoordinatesUrl);
            if (Objects.isNull(districtAddress.getDistrictLatitudeLongitude())) {
                BaiduRegionResponse forObject = restTemplate.getForObject(getRegionCoordinatesUrl, BaiduRegionResponse.class);
                logger.debug(forObject.toString());
                if (Objects.nonNull(forObject) && Objects.nonNull(forObject.getResults()) && forObject.getResults().size() > 0) {
                    List<ResultsItem> results = forObject.getResults();
                    ResultsItem result = results.get(0);
                    Location location = result.getLocation();
                    if (null != location) {
                        districtAddress.districtLatitudeLongitude(location.toString());
                        addressLibraryCoordinateRepository.save(districtAddress);
                        StringBuffer add = new StringBuffer("");
                        add.append(address);
                        add.append("-");
                        add.append(location);
                        add.append("-");
                        add.append(districtName);
                        addressWithLatitudeAndLongitude.add(add.toString());
                        logger.debug("{},返回名称：{}，经纬度：{},数据类型：{}", address, result.getName(), location, forObject.getResultType());
                    }else {
                        logger.debug("这条记录没有处理:{}",address);
                    }
                } else {
                    logger.warn("这条记录没有处理：{}", address);
                }
            } else {
                StringBuffer add = new StringBuffer("");
                add.append(address);
                add.append("-");
                add.append(districtAddress.getDistrictLatitudeLongitude());
                add.append("-");
                add.append(districtName);
                addressWithLatitudeAndLongitude.add(add.toString());
                logger.debug("从数据库中读取经纬度：地址:{},经纬度:{}", address, districtAddress.getDistrictLatitudeLongitude());
            }

            logger.debug("address:{}", address);
        });
        return addressWithLatitudeAndLongitude;
    }

    /**
     * 获取城市地址
     *
     * @param addressLibraryCoordinates 地址库坐标集合
     * @param cityCode                  城市编码
     * @return
     */
    @NotNull
    private AddressLibraryCoordinate getCityAddress(List<AddressLibraryCoordinate> addressLibraryCoordinates, String cityCode) {
        return addressLibraryCoordinates.stream().filter(addressLibraryCoordinate1 -> addressLibraryCoordinate1.getCode().equals(cityCode)).findFirst().get();
    }

    /**
     * 通过数据库获取编码
     *
     * @param cityCode
     * @return
     */
    @NotNull
    private AddressLibraryCoordinate getAddressLibraryCoordinate(String cityCode) {

        AddressLibraryCoordinate coordinate = new AddressLibraryCoordinate();
        coordinate.code(cityCode);
        Example<AddressLibraryCoordinate> coordinateExample = Example.of(coordinate);
        AddressLibraryCoordinate cityAddress = addressLibraryCoordinateRepository.findOne(coordinateExample).get();
        return cityAddress;
    }


    /**
     * 获取路径规划的相应信息
     *
     * @param origin      开始坐标点
     * @param destination 结束坐标点
     * @return
     */
    private BaiduTrunkRouterResponse getBaiduTrunkRouterResponse(String origin, String destination) {

        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("origin", origin);
        paramsMap.put("destination", destination);
        paramsMap.put("ak", ak2);
        URI uri = getUri(origin, destination, paramsMap);
        BaiduTrunkRouterResponse baiduTrunkRouterResponse = new BaiduTrunkRouterResponse();
        try {
            baiduTrunkRouterResponse = restTemplate.getForObject(uri, BaiduTrunkRouterResponse.class);
        } catch (Exception e) {
            logger.error("接口调用异常:{}", e.getMessage());
        }

        return baiduTrunkRouterResponse;
    }

    @NotNull
    private URI getUri(String origin, String destination, Map paramsMap) {
        String urlWithoutProtocolAfterEncoding = getUrlWithoutProtocolAfterEncoding(paramsMap);
        String sn = MD5(urlWithoutProtocolAfterEncoding);
        logger.debug("sn:{}", sn);

        paramsMap.put("sn", sn);
        String addSerializationCode = null;
        try {
            addSerializationCode = toQueryString(paramsMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String fullUrlWithoutEncoding = String.format(routePlanUrl, origin, destination, ak2, sn);
        String fullUrlEncoding = String.format(routePlanUrl2, addSerializationCode);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(fullUrlEncoding);
        URI uri = uriComponentsBuilder.build(true).toUri();

        logger.debug("full url without encoding:{}", fullUrlWithoutEncoding);
        logger.debug("full url with encoding:{}", fullUrlEncoding);
        return uri;
    }

    /**
     * 获取没有协议的Encoding接口
     * @param paramsMap
     * @return
     */
    @Nullable
    private String getUrlWithoutProtocolAfterEncoding(Map paramsMap) {
        String baiduQueryParamsString = null;
        try {
            baiduQueryParamsString = toQueryString(paramsMap);
        } catch (UnsupportedEncodingException e) {
            logger.error("参数转换错误:{}",e.getMessage());
        }

        logger.debug("query string:{}", baiduQueryParamsString);
        String urlWithoutProtocol = String.format(uri, baiduQueryParamsString, sk);
        logger.debug("url without protocol:{}", urlWithoutProtocol);
        String urlWithoutProtocolAfterEncoding = null;
        try {
            urlWithoutProtocolAfterEncoding = URLEncoder.encode(urlWithoutProtocol, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转换接口错误:{}",e.getMessage());
        }
        logger.debug("url encoding :{}", urlWithoutProtocolAfterEncoding);
        return urlWithoutProtocolAfterEncoding;
    }
}

