package com.rmit.au.onlinelibrarymanagementapp.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "users")
public class User {

    @Id
    public String id;
    public String username;
    public String email;
    public String password;
    public String role;

}