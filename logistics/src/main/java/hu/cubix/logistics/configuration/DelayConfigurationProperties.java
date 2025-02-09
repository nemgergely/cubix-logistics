package hu.cubix.logistics.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "logistics.transport.delay")
public class DelayConfigurationProperties {

    private Limit limit;
    private Percentage percentage;

    @Setter
    @Getter
    public static class Limit {
        private int firstCategory;
        private int secondCategory;
        private int thirdCategory;
        private int fourthCategory;
    }

    @Setter
    @Getter
    public static class Percentage {
        private int firstCategory;
        private int secondCategory;
        private int thirdCategory;
        private int fourthCategory;

    }
}
