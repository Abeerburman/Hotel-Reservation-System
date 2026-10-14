package com.hotel.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Reservation {
    public int id;
    public String name;
    public String phone;
    public String email;
    public String checkIn;   // ISO date string yyyy-MM-dd
    public String checkOut;  // ISO date string yyyy-MM-dd
    public int adults;
    public int children;
    public String note;
    public int roomId;
    public String createdAt;

    public Map<String, Object> toMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("phone", phone);
        m.put("email", email);
        m.put("checkIn", checkIn);
        m.put("checkOut", checkOut);
        m.put("adults", adults);
        m.put("children", children);
        m.put("note", note);
        m.put("roomId", roomId);
        m.put("createdAt", createdAt);
        return m;
    }
}
