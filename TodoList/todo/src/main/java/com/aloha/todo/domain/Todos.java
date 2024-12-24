package com.aloha.todo.domain;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Todos {
    private Long no;
    private String id;
    private String name;
    private Boolean status;
    private int seq;
    private Date createdAt;
    private Date updatedAt;

    public Todos() {
        this.id = UUID.randomUUID().toString();
    }
}
