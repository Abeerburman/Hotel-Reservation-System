package com.hotel.handler;

import com.hotel.model.Reservation;
import com.hotel.store.DataStore;
import com.hotel.util.Json;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GET  /api/reservations  -> list all reservations (admin view)
 * POST /api/reservations  -> create a reservation
 *   body: { name, phone, email, checkIn, checkOut, adults, children, note, roomId }
 */
public class ReservationHandler extends BaseHandler implements HttpHandler {

    private final DataStore store;

    public ReservationHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        if (handlePreflight(ex)) return;

        String method = ex.getRequestMethod();
        if ("GET".equalsIgnoreCase(method)) {
            List<Object> body = store.getReservations().stream()
                    .map(Reservation::toMap).collect(Collectors.toList());
            sendJson(ex, 200, body);
            return;
        }

        if ("POST".equalsIgnoreCase(method)) {
            handleCreate(ex);
            return;
        }

        sendError(ex, 405, "Method not allowed");
    }

    private void handleCreate(HttpExchange ex) throws IOException {
        String body = readBody(ex);
        Map<String, Object> data;
        try {
            data = Json.parseObject(body);
        } catch (Exception e) {
            sendError(ex, 400, "Invalid JSON body");
            return;
        }

        String name = str(data, "name");
        String email = str(data, "email");
        String checkIn = str(data, "checkIn");
        String checkOut = str(data, "checkOut");

        if (isBlank(name) || isBlank(email) || isBlank(checkIn) || isBlank(checkOut)) {
            sendError(ex, 400, "name, email, checkIn and checkOut are required");
            return;
        }

        Reservation r = new Reservation();
        r.name = name;
        r.phone = str(data, "phone");
        r.email = email;
        r.checkIn = checkIn;
        r.checkOut = checkOut;
        r.adults = intVal(data, "adults", 1);
        r.children = intVal(data, "children", 0);
        r.note = str(data, "note");
        r.roomId = intVal(data, "roomId", 0);

        Reservation saved = store.addReservation(r);
        sendJson(ex, 201, saved.toMap());
    }

    private String str(Map<String, Object> data, String key) {
        Object v = data.get(key);
        return v == null ? "" : v.toString();
    }

    private int intVal(Map<String, Object> data, String key, int def) {
        Object v = data.get(key);
        if (v instanceof Number) return ((Number) v).intValue();
        return def;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
