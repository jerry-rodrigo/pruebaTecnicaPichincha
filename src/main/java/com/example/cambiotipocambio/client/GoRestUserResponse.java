package com.example.cambiotipocambio.client;

import lombok.Data;

@Data
public class GoRestUserResponse {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String status;
}