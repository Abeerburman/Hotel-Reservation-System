package com.hotel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Room {
    public int id;
    public String name;
    public int price;
    public String description;
    public String imageUrl;
    public boolean available;

    public Room() {}

    public Room(int id, String name, int price, String description, String imageUrl, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.available = available;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("price", price);
        m.put("description", description);
        m.put("imageUrl", imageUrl);
        m.put("available", available);
        return m;
    }
}
