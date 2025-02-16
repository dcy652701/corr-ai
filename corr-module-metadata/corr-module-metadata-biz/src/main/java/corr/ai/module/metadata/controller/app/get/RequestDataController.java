package corr.ai.module.metadata.controller.app.get;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiProviderConstants;
import corr.ai.module.metadata.consts.UrlConstants;
import corr.ai.module.metadata.controller.app.get.req.ApiFailLogReq;
import corr.ai.module.metadata.controller.app.get.req.ApiReq;
import corr.ai.module.metadata.controller.app.get.req.ApiSaveReq;
import corr.ai.module.metadata.controller.app.get.req.MetricsListReq;
import corr.ai.module.metadata.controller.app.get.resp.ApiResp;
import corr.ai.module.metadata.controller.app.get.resp.ListMetricsResp;
import corr.ai.module.metadata.controller.app.get.resp.sub.Asset;
import corr.ai.module.metadata.converter.data.CoinAssetConverter;
import corr.ai.module.metadata.dal.dataobject.data.ApiDO;
import corr.ai.module.metadata.dal.dataobject.data.CoinAssetDO;
import corr.ai.module.metadata.mq.dto.ApiFailLogDTO;
import corr.ai.module.metadata.mq.dto.CoinAssetDTO;
import corr.ai.module.metadata.mq.producer.KafkaProducer;
import corr.ai.module.metadata.service.asset.CoinAssetService;
import corr.ai.module.metadata.service.info.ApiInfoService;
import corr.ai.module.metadata.service.log.ApiFailLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 请求数据接口的controller
 *
 * @author dongchengye
 */
@RestController
@RequestMapping("/metadata/endpoints")
@Slf4j
@Tag(name = "控制抓取数据的接口")
public class RequestDataController {

    @Value("${corr.custom.glassnode}")
    private String glassnodeApiKey;

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private ApiFailLogService apiFailLogService;

    @Autowired
    private CoinAssetService coinAssetService;

    @Autowired
    private CoinAssetConverter coinAssetConverter;

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/formatReq")
    public CommonResult<List<JSONObject>> formatReq(@RequestBody ApiReq req){
        String symbol = req.getSymbol();
        List<Long> apiId = req.getApiId();
        List<List<Long>> partition = ListUtil.partition(apiId, 381);
        ArrayList<JSONObject> result = new ArrayList<>();
        for (List<Long> p : partition) {
            JSONObject obj = JSONUtil.createObj();
            obj.set("symbol",symbol);
            obj.set("apiId",p);
            result.add(obj);
        }
        return CommonResult.success(result);
    }

    @PostMapping("/getApiList")
    @Operation(description = "获取所有的接口数据")
    public CommonResult<List<ListMetricsResp>> getApiList(@RequestBody MetricsListReq req) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UrlConstants.LIST_METRICS_URL)
                .addHeader(UrlConstants.REQUEST_HEADER_API_KEY, glassnodeApiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONArray jsonArray = JSONUtil.parseArray(Objects.requireNonNull(response.body()).string());
                List<ListMetricsResp> listMetricsResps = JSONUtil.toList(jsonArray, ListMetricsResp.class);
                List<ApiSaveReq> apiSaveReqList = new ArrayList<>();

                //筛选出符合条件的币种，并且组成实体类，同一个币种有多个接口
                //新筛选方案
                listMetricsResps.removeIf(api -> {
                    HashSet<String> assetSymbolSet = api.getAssets().stream().map(Asset::getSymbol).collect(Collectors.toCollection(HashSet::new));
                    HashSet<String> filterSymbolSet = new HashSet<>(req.getFilterSymbols());
                    assetSymbolSet.retainAll(filterSymbolSet);
                    ArrayList<String> remainSymbols = new ArrayList<>(assetSymbolSet);
                    return remainSymbols.isEmpty();
                });
                listMetricsResps.forEach(api -> {
                    //剔除不需要的币种符号
                    HashSet<String> assetSymbolSet = api.getAssets().stream().map(Asset::getSymbol).collect(Collectors.toCollection(HashSet::new));
                    HashSet<String> filterSymbolSet = new HashSet<>(req.getFilterSymbols());
                    assetSymbolSet.retainAll(filterSymbolSet);
                    ArrayList<String> remainSymbols = new ArrayList<>(assetSymbolSet);
                    for (String remainSymbol : remainSymbols) {
                        String path = api.getPath();
                        String[] split = path.split("/");
                        int length = split.length;
                        ApiSaveReq apiSaveReq = new ApiSaveReq(split[length - 1], split[length - 2], remainSymbol, path, ApiProviderConstants.GLASSNODE, api.getResolutions());
                        apiSaveReqList.add(apiSaveReq);
                    }
                });
                apiInfoService.saveValidApis(apiSaveReqList);
                return CommonResult.success(null);
            } else {
                log.error("失败");
            }
        } catch (IOException e) {
            throw new ServiceException(new ErrorCode(600, "request error"));
        }
        return CommonResult.success(null);
    }

    @PostMapping("/requestApic")
    @Operation(description = "根据接口id发送请求抓取数据,多线程版")
    public CommonResult<Boolean> sendRequestConcurrent(@RequestBody ApiReq req) {
        List<ApiDO> apiList = apiInfoService.getApiListByIds(req.getApiId());
        List<ApiResp> totalRespList = Collections.synchronizedList(new ArrayList<>());
        List<ApiFailLogReq> failLogReqs = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<List<ApiResp>>> futures = new ArrayList<>();

        for (ApiDO apiDO : apiList) {
            futures.add(executorService.submit(() -> {
                String url = apiDO.getUrl();
                String minInterval = apiDO.getMinInterval();
                log.info("请求接口：" + url);
                String totalUrl = UrlConstants.GLASSNODE_BASE_URL + url;
                HttpUrl.Builder builder = HttpUrl.parse(totalUrl).newBuilder();
                builder.addQueryParameter("a", req.getSymbol());
                builder.addQueryParameter("i", minInterval);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(builder.build())
                        .addHeader(UrlConstants.REQUEST_HEADER_API_KEY, glassnodeApiKey)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        JSONArray jsonArray = JSONUtil.parseArray(Objects.requireNonNull(response.body()).string());
                        List<ApiResp> apiRespList = JSONUtil.toList(jsonArray, ApiResp.class);
                        apiRespList.forEach(x -> {
                            x.setSymbol(req.getSymbol());
                            x.setDataType(apiDO.getApiType());
                            x.setDataName(apiDO.getApiName());
                            x.setDataFrequency(minInterval);
                        });
                        for (ApiResp apiResp : apiRespList) {
                            if (ObjectUtil.isNull(apiResp.getV())) {
                                ApiFailLogReq failLogReq = new ApiFailLogReq(apiDO.getApiId(), "can not get response", "空v", apiDO.getSymbol(), apiDO.getUrl());
                                failLogReqs.add(failLogReq);
                            }
                        }
                        return apiRespList;
                    }
                } catch (IOException e) {
                    log.error("请求接口失败：" + url, e);
                } finally {
                    if (ObjectUtil.isNotNull(response)){
                        response.close();
                    }
                }
                return Collections.emptyList();
            }));
        }

        log.info("所有线程执行完成，收集数据");
        for (Future<List<ApiResp>> future : futures) {
            try {
                totalRespList.addAll(future.get());
            } catch (Exception e) {
                log.error("获取请求结果失败", e);
            }
        }

        log.info("全部接口请求完成，总数据 " + totalRespList.size() + " 条");
        List<CoinAssetDO> coinAssetList = totalRespList.stream().map(x -> coinAssetConverter.convert(x)).collect(Collectors.toList());
        log.info("校验数据，找出空值v和非kv结构的接口");

        //结束线程池
        executorService.shutdown();
        //对数据分片，一万条为一组
        List<List<CoinAssetDO>> partition = ListUtil.partition(coinAssetList, 10000);
        log.info("数据分区完成，共 "+partition.size()+" 个分区");
        List<List<ApiFailLogReq>> failLogPartition = ListUtil.partition(failLogReqs, 1000);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());
        CountDownLatch logCountDownLatch = new CountDownLatch(failLogPartition.size());
        long begin = System.currentTimeMillis();
        for (List<CoinAssetDO> p : partition) {
            coinAssetService.insertDatas(p, countDownLatch);
        }
        for (List<ApiFailLogReq> p : failLogPartition) {
            apiFailLogService.insertLog(p, logCountDownLatch);
        }
//        try {
////            countDownLatch.getCount()
//            //要等所有线程执行完
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            log.error("阻塞异常：" + e.getMessage());
//        }
        long end = System.currentTimeMillis();
        log.info(totalRespList.size() + " 条插入完成，耗时：" + (end - begin));
        return CommonResult.success(true);
    }

    @PostMapping("/requestApi")
    @Operation(description = "根据接口id发送请求抓取数据,并发送到kafka")
    public CommonResult<Boolean> sendRequestConcurrentToMq(@RequestBody ApiReq req) {
        List<ApiDO> apiList = apiInfoService.getApiListByIds(req.getApiId());
        List<ApiResp> totalRespList = Collections.synchronizedList(new ArrayList<>());
        List<ApiFailLogReq> failLogReqs = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

//        List<Future<List<ApiResp>>> futures = new ArrayList<>();

        for (ApiDO apiDO : apiList) {
            executorService.submit(() -> {
                String url = apiDO.getUrl();
                String minInterval = apiDO.getMinInterval();
                log.info("请求接口：" + url);
                String totalUrl = UrlConstants.GLASSNODE_BASE_URL + url;
                HttpUrl.Builder builder = HttpUrl.parse(totalUrl).newBuilder();
                builder.addQueryParameter("a", req.getSymbol());
                builder.addQueryParameter("i", minInterval);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(builder.build())
                        .addHeader(UrlConstants.REQUEST_HEADER_API_KEY, glassnodeApiKey)
                        .build();
                Response response = null;
                try {
                    //请求接口拿数据
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        JSONArray jsonArray = JSONUtil.parseArray(Objects.requireNonNull(response.body()).string());
                        List<ApiResp> apiRespList = JSONUtil.toList(jsonArray, ApiResp.class);
                        apiRespList.forEach(x -> {
                            x.setSymbol(req.getSymbol());
                            x.setDataType(apiDO.getApiType());
                            x.setDataName(apiDO.getApiName());
                            x.setDataFrequency(minInterval);
                        });
                        for (ApiResp apiResp : apiRespList) {
                            if (ObjectUtil.isNull(apiResp.getV())) {
                                //如果v空，则证明是脏数据，要发送失败日志
                                ApiFailLogDTO apiFailLogDTO = new ApiFailLogDTO(apiDO.getApiId(), "null", "v是空值", apiResp.getSymbol(), apiDO.getUrl(), false, 1L);
                                kafkaProducer.sendFailLong(apiFailLogDTO);
                            }else {
                                CoinAssetDTO coinAssetDTO = coinAssetConverter.convertDTO(apiResp);
                                //适配框架的固定写法
                                coinAssetDTO.setDeleted(false);
                                coinAssetDTO.setTenantId(1L);
                                kafkaProducer.sendCoinAsset(coinAssetDTO);
                            }
                        }

                    }
                } catch (IOException e) {
                    log.error("请求接口失败：" + url, e);
                    //这里只截取一部分异常即可
                    String simpleExceptionMessage = e.getMessage().substring(0, 100);
                    ApiFailLogDTO apiFailLogDTO = new ApiFailLogDTO(apiDO.getApiId(), "null", simpleExceptionMessage, apiDO.getSymbol(), apiDO.getUrl(), false, 1L);
                    kafkaProducer.sendFailLong(apiFailLogDTO);
                } finally {
                    if (ObjectUtil.isNotNull(response)){
                        response.close();
                    }
                }
            });
        }
        return CommonResult.success(true);
    }

    @GetMapping("/listApiId")
    public CommonResult<JSONObject> listApiId(@RequestParam("sym") String sym) {
        List<Long> longs = apiInfoService.listApiId(sym);
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set("symbol", sym);
        jsonObject.set("apiId", longs);
        return CommonResult.success(jsonObject);
    }

}
