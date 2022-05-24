package com.jwt.web.controller;

import com.jwt.web.dto.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
public class ItemController {

    @GetMapping("/api/v1/items")
    public ArrayList<Item> getItems() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(new Item("itemA", 10000));
        list.add(new Item("itemB", 20000));

        return list;
    }
}
