package ru.diasoft.micro.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.diasoft.micro.domain.SmsVerification;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long>{

    Optional<SmsVerification> findBySecretCodeAndProcessGuidAndStatus(String secretCode, String processGuid, String status);

    @Transactional
    @Modifying
    @Query("update SmsVerification v set status = ?1 where processGuid = ?2")
    int updateStatusByProcessGuid(String status, String processGuid);
}
