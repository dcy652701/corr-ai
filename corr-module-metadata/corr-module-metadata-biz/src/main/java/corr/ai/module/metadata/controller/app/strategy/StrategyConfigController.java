package corr.ai.module.metadata.controller.app.strategy;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.util.object.BeanUtils;
import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.metadata.consts.CacheKey;
import corr.ai.module.metadata.controller.app.strategy.param.ListStrategyParam;
import corr.ai.module.metadata.controller.app.strategy.req.StrategyConfigReq;
import corr.ai.module.metadata.controller.app.strategy.resp.*;
import corr.ai.module.metadata.converter.strategy.StrategyConfigConverter;
import corr.ai.module.metadata.converter.strategy.TradePairConverter;
import corr.ai.module.metadata.dal.dataobject.keypair.UserKeyPairDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyHistoryDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyPageDO;
import corr.ai.module.metadata.dal.dataobject.strategy.TradePairDO;
import corr.ai.module.metadata.service.keypair.UserKeyPairService;
import corr.ai.module.metadata.service.strategy.config.StrategyConfigService;
import corr.ai.module.metadata.service.strategy.result.StrategyRecordService;
import corr.ai.module.metadata.util.CryptoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@RestController
@RequestMapping("/metadata/strategy")
@Slf4j
@Tag(name = "策略配置接口")
public class StrategyConfigController {

    @Value("${corr.ssl.keyFilePath}")
    private String ftpLocalPath;

    @Value("${corr.ssl.downloadUrl}")
    private String downloadUrl;

    @Autowired
    private StrategyConfigService strategyConfigService;

    @Autowired
    private StrategyRecordService strategyRecordService;

    @Autowired
    private UserKeyPairService userKeyPairService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StrategyConfigConverter strategyConfigConverter;

    @Autowired
    private TradePairConverter tradePairConverter;

    @PostMapping("/save")
    @PreAuthenticated
    @Operation(summary = "保存策略配置")
    public CommonResult<Long> saveConfig(@Valid @RequestBody StrategyConfigReq req) {
        if (strategyConfigService.exitsByName(req.getStrategyName())) {
            return CommonResult.error(new ErrorCode(10016, "this name is exist"));
        }
        //从缓存中读取结果
        RMap<Long, Object> paramResult = redissonClient.getMap(CacheKey.RESULT_STRING);
        Object result = paramResult.getOrDefault(req.getStrategyId(), null);
        if (ObjectUtil.isNull(result)){
            return CommonResult.error(new ErrorCode(10017,"Backtest results have expired, please run it again."));
        }
        if (JSONUtil.parseObj(result).containsKey("empty")){
            return CommonResult.error(new ErrorCode(10018,"Your strategy does not have any backtest result."));
        }
        //完成策略配置的存储
        strategyConfigService.finishStrategySave(req.getStrategyId(), req.getStrategyName(), req.getImg(), req.getInputMode(), JSONUtil.parseObj(result), req.getPriceDataQueryParam(), req.getNonPriceDataQueryParam());

//        strategyRecordService.saveResult(JSONUtil.parseObj(result), req.getStrategyId());

        // TODO 这里注释掉，暂时不需要加密
//        String strategyStr = strategyConfig.toString();
//        String publicKey = userKeyPairService.getPublicKey();
//        StrategyConfigDO strategyConfigDO = new StrategyConfigDO();
//        strategyConfigDO.setRawStrategy(strategyStr);
//        strategyConfigDO.setStrategyHash(CryptoUtil.applySha512(strategyStr));
//        strategyConfigDO.setRawStrategyCrypto(CryptoUtil.encrypt(strategyStr,CryptoUtil.getPublicKeyFromString(publicKey)));
//        strategyConfigDO.setRawStrategyCrypto("coming soon");
//        Long strategyId = strategyConfigService.save(strategyConfigDO);
        return CommonResult.success(req.getStrategyId());
    }

    @GetMapping("/page")
    @PreAuthenticated
    @Operation(summary = "列出策略")
    public CommonResult<PageResult<StrategyPageRecordVO>> listStrategy(ListStrategyParam param) {
        PageResult<StrategyPageDO> pageResult = strategyConfigService.pageStrategy(param);
        PageResult<StrategyPageRecordVO> convertResult = pageResult.map(x -> strategyConfigConverter.convert(x));
        return CommonResult.success(convertResult);
    }

    @GetMapping("/createHistory")
    @PreAuthenticated
    @Operation(summary = "历史记录，只限制20条")
    public CommonResult<List<StrategyHistoryVO>> listCreateHistory(){
        List<StrategyHistoryDO> strategyHistoryDos = strategyConfigService.listHistory();
        List<StrategyHistoryVO> historyVos = strategyHistoryDos.stream().map(x -> strategyConfigConverter.convert(x)).collect(Collectors.toList());
        return CommonResult.success(historyVos);
    }

    @GetMapping("/listPair")
    @PreAuthenticated
    @Operation(summary = "列出所有策略的交易对")
    public CommonResult<PageResult<TradePairRespVO>> listPair(PageParam param) {
        PageResult<TradePairDO> pageResult = strategyConfigService.listTradePair(param);
        return CommonResult.success(BeanUtils.toBean(pageResult, TradePairRespVO.class));
    }

    @GetMapping("/get")
    @PreAuthenticated
    @Operation(summary = "查询策略")
    public CommonResult<StrategyConfigRespVO> getStrategy(@RequestParam("sid") Long sid) {
        StrategyConfigDO strategyConfigDO = strategyConfigService.queryById(sid);
        StrategyConfigRespVO respVO = strategyConfigConverter.convert(strategyConfigDO);
        return CommonResult.success(respVO);
    }

    @PostMapping("/generateKey")
    @Operation(summary = "生成秘钥对")
    @PreAuthenticated
    public CommonResult<String> generatePrivateKey() {

        JSONObject jsonObject = CryptoUtil.generateKeyPair();
        String publicKey = jsonObject.getStr("public");
        String privateKey = jsonObject.getStr("private");

        UUID uuid = UUID.fastUUID();
        String fileName = uuid.toString(true) + ".txt";

        try (FileOutputStream output = new FileOutputStream(ftpLocalPath + fileName)) {
            // 将字符串转换为字节数组
            byte[] bytes = privateKey.getBytes();
            // 将字节数组写入文件
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10013, "秘钥保存失败"));
        }
        String url = downloadUrl + fileName;
        UserKeyPairDO userKeyPairDO = new UserKeyPairDO();
        userKeyPairDO.setPrivateKey(privateKey);
        userKeyPairDO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        userKeyPairDO.setPublicKey(publicKey);
        userKeyPairDO.setPrivateKeyFile(fileName);
        userKeyPairService.save(userKeyPairDO);
        return CommonResult.success(url);
    }

    @PostMapping("/deletePrivateKeyFile")
    @Operation(summary = "删除存秘钥的文件")
    @PreAuthenticated
    public CommonResult<Boolean> deletePrivateKeyFile() {
        String privateKeyFileName = userKeyPairService.getPrivateKeyFileName();
        File file = new File(ftpLocalPath + privateKeyFileName);
        if (file.delete()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.success(false);
        }
    }

}
