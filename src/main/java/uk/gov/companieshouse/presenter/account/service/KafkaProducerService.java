package uk.gov.companieshouse.presenter.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.kafka.KafkaMessageHelper;

@Service
public class KafkaProducerService {

    @Value("${KAFKA_TARGET_TOPIC}")
    private String targetTopic;

    private final KafkaMessageHelper messageHelper;

    private final Logger logger;

    @Autowired
    public KafkaProducerService(
            final Logger logger,
            final KafkaMessageHelper messageHelper) {
        this.logger = logger;
        this.messageHelper = messageHelper;
    }

    public void sendPresenterAccountId(final String presenterAccountDetailsId) {
        final var presenterCreated = messageHelper.createPresenterCreated(presenterAccountDetailsId);
        final byte[] byteValues = messageHelper.serializePresenterCreated(presenterCreated);
        final var message = messageHelper.createMessage(targetTopic, byteValues);
        messageHelper.sendKafkaMessage(message);
        logger.debug(String.format("message sent to Kafka - id: %s", presenterAccountDetailsId));
    }

}
