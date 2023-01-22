package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sms_verification")
public class SmsVerification {

    @Id
    @GeneratedValue(generator = "sms_verification_verificationid_seq")
    @SequenceGenerator(name = "sms_verification_verificationid_seq", sequenceName = "sms_verification_verificationid_seq", allocationSize = 1)
    @Column(name = "verificationid")
    private Long verificationId;

    @Column(name = "processguid")
    private String processGuid;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "secretcode")
    private String secretCode;

    @Column(name = "status")
    private String status;
}
