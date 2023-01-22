package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.SmsVerification;
import ru.diasoft.micro.model.ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification;
import ru.diasoft.micro.model.ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification;
import ru.diasoft.micro.repository.SmsVerificationRepository;
import ru.diasoft.micro.rest.SmsVerificationService;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class SmsVerificationPrimaryService implements SmsVerificationService {

    private final SmsVerificationRepository repository;
    @Override
    public ResponseEntity<?> myOperationId1() {
        return null;
    }

    @Override
    public ResponseEntity<ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification> dsSmsVerificationGETnonews_v1_0_SmsVerification(String path_processGUID, String path_code) {
        Optional<SmsVerification> optional = repository.findBySecretCodeAndProcessGuidAndStatus(path_code, path_processGUID, "OK");
        ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification response =new ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification();
        if (optional.isPresent()){
            SmsVerification smsVerification = optional.get();
            response.setGuid(smsVerification.getProcessGuid());
            response.setSecretCode(smsVerification.getSecretCode());
            response.setPhoneNumber(smsVerification.getPhoneNumber());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification> dsSmsVerificationPOSTnonews_v1_0_SmsVerification(String phoneNumber) {
        String guid = UUID.randomUUID().toString();
        String secretCode = String.format("%04d", new Random().nextInt(10000));

        SmsVerification smsVerification = SmsVerification.builder()
                .phoneNumber(phoneNumber)
                .processGuid(guid)
                .secretCode(secretCode)
                .status("OK")
                .build();
        repository.save(smsVerification);

//        messagingGateway.smsVerificationCreated(
//                SmsVerificationMessage.builder().guid(guid).code(secretCode).phoneNumber(phoneNumber).build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification(guid));
    }
}
