package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "verification_number")
public class VerificationNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userName;
    private Integer verificationNumber;

    public VerificationNumber(String userName) {
        this.userName = userName;
        this.verificationNumber = new Random().nextInt();
    }

    public String userName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getVerification_number() {
        return verificationNumber;
    }

    public void setVerification_number(Integer verificationNumber) {
        this.verificationNumber = verificationNumber;
    }
}
