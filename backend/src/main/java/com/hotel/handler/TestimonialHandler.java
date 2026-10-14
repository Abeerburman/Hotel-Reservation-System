package com.hotel.handler;

import com.hotel.model.Testimonial;
import com.hotel.store.DataStore;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/** GET /api/testimonials -> list all testimonials */
public class TestimonialHandler extends BaseHandler implements HttpHandler {

    private final DataStore store;

    public TestimonialHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        if (handlePreflight(ex)) return;

        if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
            sendError(ex, 405, "Method not allowed");
            return;
        }

        List<Object> body = store.getTestimonials().stream()
                .map(Testimonial::toMap).collect(Collectors.toList());
        sendJson(ex, 200, body);
    }
}
