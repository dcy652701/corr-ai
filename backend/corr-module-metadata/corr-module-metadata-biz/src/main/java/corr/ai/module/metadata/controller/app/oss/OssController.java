package corr.ai.module.metadata.controller.app.oss;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.module.metadata.service.oss.TxOssService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import static corr.ai.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static corr.ai.framework.common.pojo.CommonResult.error;
import static corr.ai.framework.common.pojo.CommonResult.success;

/**
 * @author dongchengye
 */
@RestController
@RequestMapping("/metadata/oss")
@Slf4j
public class OssController {

//    @Resource
//    private OssService ossService;

//    @PostMapping("/upload")
//    @Operation(summary = "上传文件到OSS")
//    @PreAuthenticated
//    public CommonResult<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
//        try {
//            // 上传文件的文件名称
//            String originalFilename = file.getOriginalFilename();
//            // 扩展名
//            String extName = FileNameUtil.extName(originalFilename);
//            // 文件名
//            String mainName = IdUtil.fastSimpleUUID();
//            String fileName = mainName + "." + extName;
//
//            String ossFileName = path + "/" + fileName;
//            // 替换 /// 多斜杠 为 / 适配oss的规则
//            ossFileName = ossFileName.replaceAll("(?<!://)(//+)|(///+)", "/");
//            // 替换 以 / //  开头 的字符串为 ""
//            ossFileName = ossFileName.replaceAll("(^/+)", "");
//
//            return success(ossService.uploadFile(ossFileName, new ByteArrayInputStream(file.getBytes())));
//        } catch (Exception e) {
//            log.error("文件上传失败 ", e);
//            return error(INTERNAL_SERVER_ERROR.getCode(), "文件上传失败");
//        }
//    }

    @Autowired
    private TxOssService ossService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件到OSS")
    @PreAuthenticated
    public CommonResult<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path){
        //TODO 临时写法
        try {
            // 上传文件的文件名称
            String originalFilename = file.getOriginalFilename();
            // 扩展名
            String extName = FileNameUtil.extName(originalFilename);
            // 文件名
            String mainName = IdUtil.fastSimpleUUID();
            String fileName = mainName + "." + extName;
            String ossFileName = path + "/" + fileName;
            // 替换 /// 多斜杠 为 / 适配oss的规则
            ossFileName = ossFileName.replaceAll("(?<!://)(//+)|(///+)", "/");
            // 替换 以 / //  开头 的字符串为 ""
            ossFileName = ossFileName.replaceAll("(^/+)", "");
            return success(ossService.uploadFile(ossFileName, new ByteArrayInputStream(file.getBytes())));
        } catch (Exception e) {
            log.error("文件上传失败 ", e);
            return error(INTERNAL_SERVER_ERROR.getCode(), "文件上传失败");
        }
    }
}
