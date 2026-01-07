package com.company.mentoring.cis.dto;

public class ExternalUserDto {
    private String externalId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String userType; // STUDENT / EMPLOYEE / ADMIN

    // Геттеры и сеттеры
    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
