package com.example.cambiotipocambio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("app_user")
public class AppUser {
    @Id
    private Long id;
    private String username;
    private String password;
    private String role;
    private String name;
}