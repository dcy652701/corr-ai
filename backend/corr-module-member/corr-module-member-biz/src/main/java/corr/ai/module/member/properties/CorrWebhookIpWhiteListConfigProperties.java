package corr.ai.module.member.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author dongchengye
 */
@ConfigurationProperties(prefix = "corr")
public class CorrWebhookIpWhiteListConfigProperties {

    private Usdt usdt;

    public Usdt getUsdt() {
        return usdt;
    }

    public void setUsdt(Usdt usdt) {
        this.usdt = usdt;
    }

    public static class Usdt{
        private List<String> alchemy;

        public List<String> getAlchemy() {
            return alchemy;
        }

        public void setAlchemy(List<String> alchemy) {
            this.alchemy = alchemy;
        }
    }
}
