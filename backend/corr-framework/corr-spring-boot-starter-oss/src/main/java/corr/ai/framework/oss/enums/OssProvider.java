package corr.ai.framework.oss.enums;

import lombok.Getter;

/**
 * @author dongchengye
 */
@Getter
public enum OssProvider {
    TENCENT("tencent"),
    ALIYUN("aliyun");

    private final String name;

    OssProvider(String name) {
        this.name = name;
    }

}
