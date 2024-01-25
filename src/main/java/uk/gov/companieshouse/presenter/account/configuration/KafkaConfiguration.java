package uk.gov.companieshouse.presenter.account.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.gov.companieshouse.kafka.producer.Acks;
import uk.gov.companieshouse.kafka.producer.CHKafkaProducer;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

@Configuration
public class KafkaConfiguration {

    @Value("${KAFKA_BROKER_ADDR}")
    private String kafkaBrokerAddr;

    @Value("${KAFKA_PRODUCER_ACKS_CONFIG}")
    private String acknowledgementConfig;

    @Value("${KAFKA_PRODUCER_MAXIMUM_RETRY_ATTEMPTS}")
    private int maximumRetryAttempts;

    @Bean
    public ProducerConfig producerConfig() {
        final var config = new ProducerConfig();
        config.setAcks(Acks.valueOf(acknowledgementConfig));
        config.setBrokerAddresses(Arrays.asList(kafkaBrokerAddr).toArray(new String[0]));
        config.setRetries(maximumRetryAttempts);
        config.setRoundRobinPartitioner(false);
        return config;
    }

    @Bean
    public CHKafkaProducer chKafkaProducer() {
        return new CHKafkaProducer(producerConfig());
    }
}
