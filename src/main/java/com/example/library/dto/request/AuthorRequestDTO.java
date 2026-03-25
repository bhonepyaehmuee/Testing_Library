package com.example.library.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDTO {
    private String name;
    private String email;
}
