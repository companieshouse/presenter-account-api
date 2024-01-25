package uk.gov.companieshouse.presenter.account.kafka;

import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.kafka.producer.CHKafkaProducer;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.exceptionhandler.KafkaMessageFailException;
import uk.gov.companieshouse.presenter.account.exceptionhandler.KafkaMessageInterruptedException;
import uk.gov.companieshouse.presenter.account.serialiser.PresenterCreatedSerialiser;
import uk.gov.companieshouse.presenter.account.utils.DateTimeGenerator;
import uk.gov.companieshouse.presenter_account.PresenterCreated;

@Component
public class KafkaMessageHelper {

    private static final String KAFKA_INTERRUPT_MESSAGE = "Kafka message interrupted while senting";
    private static final String ERROR_MESSAGE = "Failed to send Kafka message";

    private final CHKafkaProducer kafkaProducer;

    private final DateTimeGenerator dateTime;

    private final PresenterCreatedSerialiser serialiser;

    private final Logger logger;

    @Autowired
    public KafkaMessageHelper(
            CHKafkaProducer kafkaProducer,
            DateTimeGenerator dateTime,
            PresenterCreatedSerialiser serialiser,
            Logger logger) {
        this.dateTime = dateTime;
        this.kafkaProducer = kafkaProducer;
        this.logger = logger;
        this.serialiser = serialiser;
    }

    public PresenterCreated createPresenterCreated(final String presenterAccountDetailId) {
        Objects.requireNonNull(presenterAccountDetailId);

        final var kafkaRecord = new PresenterCreated();
        kafkaRecord.setId(presenterAccountDetailId);
        return kafkaRecord;
    }

    public byte[] serializePresenterCreated(final PresenterCreated presenterCreated) {
        Objects.requireNonNull(presenterCreated);
        return serialiser.serialize(presenterCreated);
    }

    public Message createMessage(final String topic, final byte[] value) {
        Objects.requireNonNull(topic);
        Objects.requireNonNull(value);

        if (topic.isBlank()) {
            throw new IllegalArgumentException("Topic can not be empty");
        }

        final Message message = new Message();
        message.setTopic(topic);
        message.setValue(value);
        message.setTimestamp(dateTime.currentDateTime().toEpochSecond(ZoneOffset.UTC));
        return message;
    }

    public void sendKafkaMessage(final Message message) {
        final Future<RecordMetadata> messageFuture = kafkaProducer.sendAndReturnFuture(message);
        try {
            messageFuture.get();
        } catch (final ExecutionException e) {
            logger.error(ERROR_MESSAGE, e);
            throw new KafkaMessageFailException(ERROR_MESSAGE);
        } catch (final InterruptedException e) {
            logger.error(KAFKA_INTERRUPT_MESSAGE, e);
            Thread.currentThread().interrupt();
            throw new KafkaMessageInterruptedException(KAFKA_INTERRUPT_MESSAGE);
        }

    }
}
