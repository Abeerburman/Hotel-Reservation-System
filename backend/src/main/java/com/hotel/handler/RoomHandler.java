package com.hotel.handler;

import com.hotel.model.Room;
import com.hotel.store.DataStore;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * GET /api/rooms          -> list all rooms
 * GET /api/rooms/{id}     -> single room
 */
public class RoomHandler extends BaseHandler implements HttpHandler {

    private static final String PREFIX = "/api/rooms";
    private final DataStore store;

    public RoomHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        if (handlePreflight(ex)) return;

        if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
            sendError(ex, 405, "Method not allowed");
            return;
        }

        String idPart = pathAfter(ex, PREFIX);
        if (idPart.isEmpty()) {
            List<Object> body = store.getRooms().stream().map(Room::toMap).collect(Collectors.toList());
            sendJson(ex, 200, body);
            return;
        }

        try {
            int id = Integer.parseInt(idPart);
            Optional<Room> room = store.getRoom(id);
            if (room.isPresent()) {
                sendJson(ex, 200, room.get().toMap());
            } else {
                sendError(ex, 404, "Room not found");
            }
        } catch (NumberFormatException e) {
            sendError(ex, 400, "Invalid room id");
        }
    }
}
