package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "verification_number")
public class VerificationNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer verificationNumber;

    public VerificationNumber(Integer userId) {
        this.userId = userId;
        this.verificationNumber = new Random().nextInt();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVerification_number() {
        return verificationNumber;
    }

    public void setVerification_number(Integer verificationNumber) {
        this.verificationNumber = verificationNumber;
    }
}
