package com.jwt.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item {

    private String itemName;
    private int price;
}
