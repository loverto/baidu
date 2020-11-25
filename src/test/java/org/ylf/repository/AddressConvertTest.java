package org.ylf.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
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

//        List<String> logisticsCentersAndDistrictsResult = getLogisticsCentersAndDistrictsResult(addressWithLatitudeAndLongitude, logisticsCenters, logisticsCenterAndDistrictsCoordinateResult);
//        logisticsCentersAndDistrictsResult.stream().forEach(System.out::println);

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
            return logisticsCenters.stream().map(logisticsCenter -> logisticsCenter.getLogisticsCenterName()).collect(Collectors.toList()).stream().anyMatch(logisticsCenterName -> logisticsCenterName.contains(provinceName));
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

    /**
     * 获取不包含物流中心的区县与物流中心的距离
     *
     * @param logisticsCenters    物流中心
     * @param noneLogisticsCenter 包含物流中心的区县包含坐标
     * @return
     */
    private List<String> getNonologisticsCentersAndDistrictsResult(List<LogisticsCenter> logisticsCenters, List<String> noneLogisticsCenter) {
        logger.info("无物流中心的区县：{}", noneLogisticsCenter.size());

        List<String> nonologisticsCentersAndDistrictsResult = new ArrayList<>();
        File nonFile = new File("nonlogisticsCenters.csv");
        // 计算非物流中心覆盖区域
        noneLogisticsCenter.stream().forEach(address -> {
                String[] split = address.split("-");
                String addr = split[0];
                String location = split[1];
                String districtName = split[2];
                List<String> dis = new ArrayList<>();
                // 计算各物流中心的距离
                logisticsCenters.stream().forEach(logisticsCenter -> {
                    String longitude = logisticsCenter.getLongitude();
                    String dimension = logisticsCenter.getDimension();
                    String origin = dimension + "," + longitude;
                    BaiduTrunkRouterResponse forObject = getBaiduTrunkRouterResponse(origin, location);
                    Result result = forObject.getResult();
                    if (Objects.nonNull(forObject) && Objects.nonNull(result) && Objects.nonNull(result.getRoutes()) && result.getRoutes().size() > 0) {
                        double distance = result.getRoutes().get(0).getDistance();
                        double v = distance / 1000;
                        String returnResult = "";
                        returnResult = logisticsCenter.getLogisticsCenterName() + "-" + v + "-" + origin;
                        dis.add(returnResult);
                    }
                });

                Optional<String> shortestDistance = dis.stream().min((u1, u2) -> {
                    Double aDouble = Double.valueOf(u1.split("-")[1]);
                    Double bDouble = Double.valueOf(u2.split("-")[1]);
                    return aDouble.compareTo(bDouble);

                });
                Optional<String> secondShortDistance = dis.stream().filter(rr -> !rr.equals(shortestDistance)).min((u1, u2) -> {
                    Double aDouble = Double.valueOf(u1.split("-")[1]);
                    Double bDouble = Double.valueOf(u2.split("-")[1]);
                    return aDouble.compareTo(bDouble);
                });

                Optional<String> jingnanLogisticsCenter = dis.stream().filter(logisticsCenter -> logisticsCenter.contains("京南物流中心")).findFirst();
                Optional<String> tianjinLogisticsCenter = dis.stream().filter(logisticsCenter -> logisticsCenter.contains("天津物流中心")).findFirst();
                String cityName = addr.split(",")[0];
                String shortestDistanceLogisticsCenterName = shortestDistance.orElse(" -").split("-")[0];
                String shortestDistanceLogisticsCenter = shortestDistance.orElse(" -0").split("-")[1];
                String secondShortDistanceLogisticsCenterName = secondShortDistance.orElse(" -").split("-")[0];
                String secondShortDistanceLogisticsCenter = secondShortDistance.orElse("-0").split("-")[1];
                String jingnanLogisticsCenterName = jingnanLogisticsCenter.orElse("-").split("-")[0];
                String jingnanDistanceLogisticsCenter = jingnanLogisticsCenter.orElse("-").split("-")[1];
                String tianjinLogisticsCenterName = tianjinLogisticsCenter.orElse("-").split("-")[0];
                String tianjinDistanceLogisticsCenter = tianjinLogisticsCenter.orElse("-").split("-")[1];
                String returnResult2 = districtName + "," + location + "," + cityName + "," + shortestDistanceLogisticsCenterName + "," + shortestDistanceLogisticsCenter + "公里" + "," + secondShortDistanceLogisticsCenterName + "," + secondShortDistanceLogisticsCenter + "公里" + "," + jingnanLogisticsCenterName + "," + jingnanDistanceLogisticsCenter + "公里" + "," + tianjinLogisticsCenterName + "," + tianjinDistanceLogisticsCenter + "公里";
                try {
                    FileUtils.writeStringToFile(nonFile, returnResult2 + "\n", "GBK", true);
                } catch (IOException e) {
                    logger.error("文件写入失败：{}", e.getMessage());
                }
                nonologisticsCentersAndDistrictsResult.add(returnResult2);

            }

        );
        return nonologisticsCentersAndDistrictsResult;
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
            String dimension = logisticsCenter.getDimension();
            String origin = dimension + "," + longitude;

            // 过滤出本中心仓对应的省份
            String regionName = logisticsCenter.getLogisticsCenterName();
            List<String> dataCorrespondingToThisArea = addressWithLatitudeAndLongitude.stream().filter(address -> {
                String[] split = address.split("-");
                String provinceName = split[0].split(",")[3];
                return regionName.contains(provinceName);
            }).collect(Collectors.toList());

            // 取出其他物流中心，排除掉本物流中心
            List<LogisticsCenter> otherLogisticsCenters = logisticsCenters.stream().filter(logisticsCenter1 -> !logisticsCenter1.getRegionName().equals(logisticsCenter.getRegionName())
            ).collect(Collectors.toList());

            dataCorrespondingToThisArea.stream().forEach(address -> {
                String[] split = address.split("-");
                String addr = split[0];
                String location = split[1];
                String districtName = split[2];
                BaiduTrunkRouterResponse forObject = getBaiduTrunkRouterResponse(origin, location);
                Result result = forObject.getResult();
                if (Objects.nonNull(forObject) && Objects.nonNull(result) && Objects.nonNull(result.getRoutes()) && result.getRoutes().size() > 0) {
                    double distance = result.getRoutes().get(0).getDistance();
                    double v = distance / 1000;
                    String returnResult = "";
                    String cityName = addr.split(",")[0];
                    if (v < 150) {
                        // 区域中心仓
                        //区县 经度 纬度 地市 新规则中心仓 所属中心仓 与中心仓距离 临近中心仓名称 临近中心仓距离
                        returnResult = districtName + "," + location + "," + cityName + "," + regionName + "," + regionName + "," + v + "公里" + "," + regionName + "," + v + "公里";
                    }else {
                        String shortestDistance = getShortestDistance(otherLogisticsCenters, location);
                        Double v2 = Double.valueOf(shortestDistance.split("-")[1]);
                        String otherLogisticsCenterName = shortestDistance.split("-")[0];
                        double distanceDifference = v - v2;
                        String regionalCenter = districtName + "," + location + "," + cityName + "," + regionName + "," + regionName + "," + v + "公里" + "," + otherLogisticsCenterName + "," + v2 + "公里";
                        String otherRegionalCenters = districtName + "," + location + "," + cityName + "," + otherLogisticsCenterName + "," + regionName + "," + v + "公里" + "," + otherLogisticsCenterName + "," + v2 + "公里";
                        if ((v < 300 && v >= 150&&v2<150&&distanceDifference>50)||v > 300&&v2<300&&!(distanceDifference<=100&&v/v2<=2)){
                            returnResult = otherRegionalCenters;
                        }else {
                            returnResult = regionalCenter;
                        }
                    }
                    try {
                        FileUtils.writeStringToFile(file, returnResult + "\n", "GBK", true);
                    } catch (IOException e) {
                        logger.error("文件写入失败：{}", e.getMessage());
                    }
                    logger.info("结果：{}", returnResult);
                    logisticsCentersAndDistrictsResult.add(returnResult);
                } else {
                    logger.warn("路径信息未取到，{}", "");
                }


            });


        });
        return logisticsCentersAndDistrictsResult;
    }

    @NotNull
    private String getShortestDistance(List<LogisticsCenter> otherLogisticsCenters, String location) {
        List<String> dis = new ArrayList<>();
        otherLogisticsCenters.forEach(otherLogisticsCenter -> {
            String longitude2 = otherLogisticsCenter.getLongitude();
            String dimension2 = otherLogisticsCenter.getDimension();
            String regionName1 = otherLogisticsCenter.getLogisticsCenterName();
            String origin2 = dimension2 + "," + longitude2;
            BaiduTrunkRouterResponse forObject2 = getBaiduTrunkRouterResponse(origin2, location);
            Result result2 = forObject2.getResult();
            if (Objects.nonNull(forObject2) && Objects.nonNull(result2) && Objects.nonNull(result2.getRoutes()) && result2.getRoutes().size() > 0) {
                double distance2 = result2.getRoutes().get(0).getDistance();
                double v2 = distance2 / 1000;
                String returnResult2 = "";
                returnResult2 = regionName1 + "-" + v2 + "-" + origin2;
                dis.add(returnResult2);

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

    @NotNull
    private List<String> getAddressWithLatitudeAndLongitude() {
        AddressLibraryCoordinate addressLibraryCoordinate = new AddressLibraryCoordinate();
        addressLibraryCoordinate.addrLevel("103");
        addressLibraryCoordinate.available(100);
        Example<AddressLibraryCoordinate> of = Example.of(addressLibraryCoordinate);


        List<AddressLibraryCoordinate> addressLibraries = addressLibraryCoordinateRepository.findAll(of);
        addressLibraryCoordinate = new AddressLibraryCoordinate();
        addressLibraryCoordinate.available(100);
        Example<AddressLibraryCoordinate> addressLibraryCoordinateExample = Example.of(addressLibraryCoordinate);

        List<String> addressArrayList = new ArrayList<>();


        List<String> addressWithLatitudeAndLongitude = new ArrayList<>();

        // 处理数据
        addressLibraries.stream().forEach(districtAddress -> {
            String cityCode = districtAddress.getParentCode();
            String districtName = districtAddress.getName();
            AddressLibraryCoordinate coordinate = new AddressLibraryCoordinate();
            coordinate.code(cityCode);
            Example<AddressLibraryCoordinate> coordinateExample = Example.of(coordinate);
            AddressLibraryCoordinate cityAddress = addressLibraryCoordinateRepository.findOne(coordinateExample).get();
            String cityName = cityAddress.getName();
            String provinceCode = cityAddress.getParentCode();
            coordinate = new AddressLibraryCoordinate();
            coordinate.code(provinceCode);
            Example<AddressLibraryCoordinate> coordinateExample1 = Example.of(coordinate);
            AddressLibraryCoordinate provinceAddress = addressLibraryCoordinateRepository.findOne(coordinateExample1).get();
            String provinceName = provinceAddress.getName();//addressLibraries1.stream().filter(addressLibrary2 -> provinceCode.equals(addressLibrary2.getCode())).findFirst().get().getName();
            String fullAddress = provinceName + cityName + districtName;
            provinceName = provinceName.replaceAll("市", "").replace("省", "");
            String address = cityName + "," + fullAddress + "," + districtName + "," + provinceName;

            String get = String.format(getCoordinatesUrl, fullAddress, cityName, ak);
            if (Objects.isNull(districtAddress.getDistrictLatitudeLongitude())) {
                BaiduRegionResponse forObject = restTemplate.getForObject(get, BaiduRegionResponse.class);
                logger.debug(forObject.toString());
                if (Objects.nonNull(forObject) && Objects.nonNull(forObject.getResults()) && forObject.getResults().size() > 0) {
                    List<ResultsItem> results = forObject.getResults();
                    ResultsItem result = results.get(0);
                    Location location = result.getLocation();
                    districtAddress.districtLatitudeLongitude(Objects.nonNull(location) ? location.toString() : null);
                    addressLibraryCoordinateRepository.save(districtAddress);
                    StringBuffer add = new StringBuffer("");
                    add.append(address);
                    add.append("-");
                    add.append(location);
                    add.append("-");
                    add.append(districtName);
                    addressWithLatitudeAndLongitude.add(add.toString());
                    logger.debug("{},返回名称：{}，经纬度：{},数据类型：{}", address, result.getName(), location, forObject.getResultType());
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

            addressArrayList.add(address);
            logger.debug("address:{}", address);
        });

        logger.info("处理后的地址数量：{}", addressArrayList.size());
        return addressWithLatitudeAndLongitude;
    }


    /**
     * 获取路径规划的相应信息
     *
     * @param origin
     * @param location
     * @return
     */
    private BaiduTrunkRouterResponse getBaiduTrunkRouterResponse(String origin, String location) {
        String encode;
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("origin", origin);
        paramsMap.put("destination", location);
        paramsMap.put("ak", ak2);
        String s = null;
        try {
            s = toQueryString(paramsMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        logger.info("query string:{}", s);
        encode = String.format(uri, s, sk);
        logger.info("url:{}", encode);
        String encode1 = null;
        try {
            encode1 = URLEncoder.encode(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("url encoding :{}", encode1);
        String sn = MD5(encode1);
        logger.error("sn:{}", sn);

        paramsMap.put("sn", sn);
        String s1 = null;
        try {
            s1 = toQueryString(paramsMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String format = String.format(routePlanUrl, origin, location, ak2, sn);
        String format1 = String.format(routePlanUrl2, s1);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(format1);
        URI uri = uriComponentsBuilder.build(true).toUri();

        logger.info("url:{}", format);
        BaiduTrunkRouterResponse forObject = restTemplate.getForObject(uri, BaiduTrunkRouterResponse.class);
        return forObject;
    }
}

