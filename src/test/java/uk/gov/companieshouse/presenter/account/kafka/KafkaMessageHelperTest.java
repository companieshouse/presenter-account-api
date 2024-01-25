package uk.gov.companieshouse.presenter.account.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.kafka.producer.CHKafkaProducer;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.exceptionhandler.KafkaMessageFailException;
import uk.gov.companieshouse.presenter.account.exceptionhandler.KafkaMessageInterruptedException;
import uk.gov.companieshouse.presenter.account.serialiser.PresenterCreatedSerialiser;
import uk.gov.companieshouse.presenter.account.utils.DateTimeGenerator;
import uk.gov.companieshouse.presenter_account.PresenterCreated;

@ExtendWith(MockitoExtension.class)
class KafkaMessageHelperTest {

    @Mock
    private CHKafkaProducer kafkaProducer;

    @Mock
    DateTimeGenerator dateTime;

    @Mock
    private PresenterCreatedSerialiser serialiser;

    @Mock
    private Logger logger;

    private KafkaMessageHelper messageHelper;

    private static final String ID = "id";

    private static final String TOPIC = "topic";

    private static final byte[] VALUE = new byte[] { 0 };

    private static final LocalDateTime TIME = LocalDateTime.of(2100, 1, 1, 1, 1);

    @BeforeEach
    void BeforeEach() {
        messageHelper = new KafkaMessageHelper(kafkaProducer,
                dateTime, serialiser, logger);
    }

    @Test
    @DisplayName("Create presenter created with id")
    void testCreatePresenterCreated() {
        var presenterCreated = messageHelper.createPresenterCreated(ID);
        assertEquals(ID, presenterCreated.getId());
    }

    @Test
    @DisplayName("Create presenter created with null id")
    void testCreatePresenterCreatedWithNullId() {
        assertThrows(NullPointerException.class, () -> messageHelper.createPresenterCreated(null));
    }

    @Test
    @DisplayName("Serialize presenter-created")
    void testSerializePresenterCreated() {
        PresenterCreated presenterCreated = mock(PresenterCreated.class);
        when(serialiser.serialize(presenterCreated)).thenReturn(VALUE);
        assertEquals(VALUE, messageHelper.serializePresenterCreated(presenterCreated));
        verify(serialiser, times(1)).serialize(presenterCreated);
    }

    @Test
    @DisplayName("Create message")
    void testCreateMessage() {
        when(dateTime.currentDateTime()).thenReturn(TIME);
        Message message = messageHelper.createMessage(TOPIC, VALUE);
        assertEquals(TOPIC, message.getTopic());
        assertEquals(VALUE, message.getValue());
        assertEquals(TIME.toEpochSecond(ZoneOffset.UTC), message.getTimestamp().longValue());
    }

    @Test
    @DisplayName("Create message with blank topic")
    void testCreateMessageWithBlankTopic() {
        String blankTopic = "  ";
        assertThrows(IllegalArgumentException.class, () -> {
            messageHelper.createMessage(blankTopic, VALUE);
        });
    }

    @Test
    @DisplayName("Sent presenter create message")
    void testSendPresenterCreateMessage() {
        Message message = mock(Message.class);
        Future<RecordMetadata> messageFuture = mock(Future.class);
        when(kafkaProducer.sendAndReturnFuture(message)).thenReturn(messageFuture);
        messageHelper.sendKafkaMessage(message);
        verify(kafkaProducer, times(1)).sendAndReturnFuture(message);
    }

    @Test
    @DisplayName("Sent presenter create message - Fail Exception is raised")
    void testSendPresenterCreateMessageMessageFailException() throws InterruptedException, ExecutionException {
        Message message = mock(Message.class);
        Future<RecordMetadata> messageFuture = mock(Future.class);
        when(kafkaProducer.sendAndReturnFuture(message)).thenReturn(messageFuture);
        when(messageFuture.get()).thenThrow(ExecutionException.class);
        assertThrows(KafkaMessageFailException.class, () -> messageHelper.sendKafkaMessage(message));
    }

    @Test
    @DisplayName("Sent presenter create message - Interrupted Exception is raised")
    void testSendPresenterCreateMessageMessageInterruptedException() throws InterruptedException, ExecutionException {
        Message message = mock(Message.class);
        Future<RecordMetadata> messageFuture = mock(Future.class);
        when(kafkaProducer.sendAndReturnFuture(message)).thenReturn(messageFuture);
        when(messageFuture.get()).thenThrow(InterruptedException.class);
        assertThrows(KafkaMessageInterruptedException.class, () -> messageHelper.sendKafkaMessage(message));
    }
}
