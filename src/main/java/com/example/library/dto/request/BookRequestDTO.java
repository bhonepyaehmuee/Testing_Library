package com.example.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    private String name;
    private String isbn;
    private double price;
    private String authors;
//    private List<Long> categoryIds;
}
