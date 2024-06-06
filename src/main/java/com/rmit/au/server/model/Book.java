package com.rmit.au.server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "books")
public class Book {

    @Id
    public String id;
    public String title;
    public String content;

}