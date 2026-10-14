package com.hotel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Testimonial {
    public int id;
    public String name;
    public String review;
    public String imageUrl;

    public Testimonial() {}

    public Testimonial(int id, String name, String review, String imageUrl) {
        this.id = id;
        this.name = name;
        this.review = review;
        this.imageUrl = imageUrl;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("review", review);
        m.put("imageUrl", imageUrl);
        return m;
    }
}
