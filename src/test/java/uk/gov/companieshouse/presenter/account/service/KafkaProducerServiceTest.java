package uk.gov.companieshouse.presenter.account.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.presenter.account.kafka.KafkaMessageHelper;
import uk.gov.companieshouse.presenter_account.PresenterCreated;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private Logger logger;

    @Mock
    private KafkaMessageHelper messageHelper;

    private KafkaProducerService kafkaProducerService;

    private static final String TARGET_TOPIC = "target_topic";

    private static final String ID = "id";

    @BeforeEach
    void beforeEach() {
        kafkaProducerService = new KafkaProducerService(logger, messageHelper);
        ReflectionTestUtils.setField(kafkaProducerService, "targetTopic", TARGET_TOPIC);
    }

    @Test
    @DisplayName("Test send presenter account id")
    void testSendPresenterAccountId() {
        final PresenterCreated presenterCreated = mock(PresenterCreated.class);
        final byte[] value = new byte[] {};
        final Message message = mock(Message.class);
        when(messageHelper.createPresenterCreated(ID)).thenReturn(presenterCreated);
        when(messageHelper.serializePresenterCreated(presenterCreated)).thenReturn(value);
        when(messageHelper.createMessage(TARGET_TOPIC, value)).thenReturn(message);
        kafkaProducerService.sendPresenterAccountId(ID);
        verify(messageHelper, times(1)).createPresenterCreated(ID);
        verify(messageHelper, times(1)).serializePresenterCreated(presenterCreated);
        verify(messageHelper, times(1)).createMessage(TARGET_TOPIC, value);
        verify(messageHelper, times(1)).sendKafkaMessage(message);
    }
}
