package com.hotel;

import com.hotel.handler.ReservationHandler;
import com.hotel.handler.RoomHandler;
import com.hotel.handler.TestimonialHandler;
import com.hotel.store.DataStore;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Hotel Reservation System — backend.
 * Plain Java only: no Spring, no external frameworks or dependencies.
 * Uses the JDK's built-in com.sun.net.httpserver.HttpServer for REST endpoints,
 * an in-memory DataStore (seeded with sample rooms/testimonials), and a
 * hand-written JSON reader/writer (see util.Json).
 *
 * Run with:
 *   javac -d out $(find src/main/java -name "*.java")
 *   java -cp out com.hotel.Main
 *
 * The server starts on http://localhost:8080
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }

        DataStore store = new DataStore();

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/rooms", new RoomHandler(store));
        server.createContext("/api/reservations", new ReservationHandler(store));
        server.createContext("/api/testimonials", new TestimonialHandler(store));
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();

        System.out.println("Hotel Reservation backend running at http://localhost:" + port);
        System.out.println("Endpoints:");
        System.out.println("  GET  /api/rooms");
        System.out.println("  GET  /api/rooms/{id}");
        System.out.println("  GET  /api/testimonials");
        System.out.println("  GET  /api/reservations");
        System.out.println("  POST /api/reservations");
    }
}
