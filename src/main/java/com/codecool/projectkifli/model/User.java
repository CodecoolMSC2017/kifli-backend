package com.codecool.projectkifli.model;

import com.codecool.projectkifli.dto.UserDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String premiumExpirationDate;

    @ElementCollection
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Column(name = "role")
    private List<String> roles;

    public User() {}

    public Integer getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getPremiumExpirationDate() {
        return premiumExpirationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setPremiumExpirationDate(String premiumExpirationDate) {
        this.premiumExpirationDate = premiumExpirationDate;
    }

    public UserDto toDto() {
        return new UserDto(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
