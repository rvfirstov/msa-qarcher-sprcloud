package ru.diasoft.micro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.micro.domain.SmsVerification;
import ru.diasoft.micro.model.ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification;
import ru.diasoft.micro.model.ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification;
import ru.diasoft.micro.repository.SmsVerificationRepository;
import ru.diasoft.micro.service.SmsVerificationPrimaryService;

import ru.diasoft.micro.service.smsverificationcreated.publish.SmsVerificationCreatedPublishGateway;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsVerificationServiceTest {

    @Mock
    private SmsVerificationRepository repository;

    @Mock
    private SmsVerificationCreatedPublishGateway messagingGateway;


    private SmsVerificationPrimaryService service;

    private final String PHONE_NUMBER = "+4957807575";
    private final String VALID_SECRET_CODE = "0007";
    private final String INVALID_SECRET_CODE = "0008";
    private final String GUID = UUID.randomUUID().toString();
    private final String STATUS = "OK";

    @Before
    public void init() {
        service = new SmsVerificationPrimaryService(repository, messagingGateway);
        service = new SmsVerificationPrimaryService(repository);

        SmsVerification smsVerification = SmsVerification.builder()
                .processGuid(GUID)
                .phoneNumber("12344343")
                .secretCode(VALID_SECRET_CODE)
                .status(STATUS)
                .build();

        when(repository.findBySecretCodeAndProcessGuidAndStatus(VALID_SECRET_CODE, GUID, STATUS))
                .thenReturn(Optional.of(smsVerification));
    }

    @Test
    public void testDsSmsVerificationGETnonews_v1_0_SmsVerification() {
        ResponseEntity<ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification> response = service
                .dsSmsVerificationGETnonews_v1_0_SmsVerification(GUID, VALID_SECRET_CODE);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDsSmsVerificationPOSTnonews_v1_0_SmsVerification() {
        ResponseEntity<ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification> response = service
                .dsSmsVerificationPOSTnonews_v1_0_SmsVerification(PHONE_NUMBER);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getGuid()).isNotEmpty();
    }
}
