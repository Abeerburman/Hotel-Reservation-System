package com.hotel.store;

import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.model.Testimonial;
import com.hotel.util.Json;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple in-memory data store, seeded with sample rooms/testimonials.
 * Reservations are additionally persisted to reservations.json on disk
 * (plain file, no database) so bookings survive a server restart.
 */
public class DataStore {

    private final List<Room> rooms = new ArrayList<>();
    private final List<Testimonial> testimonials = new ArrayList<>();
    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger reservationIdSeq = new AtomicInteger(1);

    private static final Path DATA_FILE = Paths.get("reservations.json");

    public DataStore() {
        seedRooms();
        seedTestimonials();
        loadReservations();
    }

    private void seedRooms() {
        rooms.add(new Room(1, "Deluxe Single Room", 2500,
                "A cozy single room with a queen bed, city view, and free Wi-Fi.",
                "/images/room1.jpg", true));
        rooms.add(new Room(2, "Executive Double Room", 4200,
                "Spacious double room with a king bed, work desk, and minibar.",
                "/images/room2.jpg", true));
        rooms.add(new Room(3, "Family Suite", 6800,
                "Two-bedroom suite ideal for families, with a living area and balcony.",
                "/images/room3.jpg", true));
        rooms.add(new Room(4, "Presidential Suite", 12000,
                "Our most luxurious suite with a private lounge, jacuzzi, and skyline views.",
                "/images/room4.jpg", false));
    }

    private void seedTestimonials() {
        testimonials.add(new Testimonial(1, "Ananya Sharma",
                "Wonderful stay, the staff were incredibly attentive and the room was spotless.",
                "/images/person1.jpg"));
        testimonials.add(new Testimonial(2, "Rohit Verma",
                "Great location and the breakfast spread was fantastic. Will book again.",
                "/images/person2.jpg"));
        testimonials.add(new Testimonial(3, "Priya Nair",
                "Booking online was effortless and check-in took less than five minutes.",
                "/images/person3.jpg"));
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public Optional<Room> getRoom(int id) {
        return rooms.stream().filter(r -> r.id == id).findFirst();
    }

    public List<Testimonial> getTestimonials() {
        return Collections.unmodifiableList(testimonials);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation addReservation(Reservation r) {
        r.id = reservationIdSeq.getAndIncrement();
        r.createdAt = Instant.now().toString();
        reservations.add(r);
        persistReservations();
        return r;
    }

    // ---------- persistence (plain JSON file, no database/ORM) ----------

    @SuppressWarnings("unchecked")
    private void loadReservations() {
        if (!Files.exists(DATA_FILE)) return;
        try {
            String content = Files.readString(DATA_FILE);
            if (content.isBlank()) return;
            List<Object> list = (List<Object>) Json.parse(content);
            int maxId = 0;
            for (Object o : list) {
                Map<String, Object> m = (Map<String, Object>) o;
                Reservation r = new Reservation();
                r.id = ((Number) m.get("id")).intValue();
                r.name = (String) m.get("name");
                r.phone = (String) m.get("phone");
                r.email = (String) m.get("email");
                r.checkIn = (String) m.get("checkIn");
                r.checkOut = (String) m.get("checkOut");
                r.adults = m.get("adults") == null ? 0 : ((Number) m.get("adults")).intValue();
                r.children = m.get("children") == null ? 0 : ((Number) m.get("children")).intValue();
                r.note = (String) m.get("note");
                r.roomId = m.get("roomId") == null ? 0 : ((Number) m.get("roomId")).intValue();
                r.createdAt = (String) m.get("createdAt");
                reservations.add(r);
                maxId = Math.max(maxId, r.id);
            }
            reservationIdSeq.set(maxId + 1);
        } catch (IOException e) {
            System.err.println("Could not load reservations.json: " + e.getMessage());
        }
    }

    private synchronized void persistReservations() {
        List<Object> list = new ArrayList<>();
        for (Reservation r : reservations) list.add(r.toMap());
        try {
            Files.writeString(DATA_FILE, Json.write(list));
        } catch (IOException e) {
            System.err.println("Could not write reservations.json: " + e.getMessage());
        }
    }
}
