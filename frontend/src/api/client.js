const BASE_URL = "http://localhost:8080/api";

async function request(path, options = {}) {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (!res.ok) {
    let message = `Request failed (${res.status})`;
    try {
      const body = await res.json();
      if (body.error) message = body.error;
    } catch {
      // ignore parse errors on error responses
    }
    throw new Error(message);
  }
  if (res.status === 204) return null;
  return res.json();
}

export const api = {
  getRooms: () => request("/rooms"),
  getRoom: (id) => request(`/rooms/${id}`),
  getTestimonials: () => request("/testimonials"),
  getReservations: () => request("/reservations"),
  createReservation: (data) =>
    request("/reservations", {
      method: "POST",
      body: JSON.stringify(data),
    }),
};
