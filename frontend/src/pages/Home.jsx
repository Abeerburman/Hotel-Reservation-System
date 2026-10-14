import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { api } from "../api/client";
import RoomCard from "../components/RoomCard";

export default function Home() {
  const [rooms, setRooms] = useState([]);
  const [testimonials, setTestimonials] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    api.getRooms().then((data) => setRooms(data.slice(0, 3))).catch((e) => setError(e.message));
    api.getTestimonials().then(setTestimonials).catch(() => {});
  }, []);

  return (
    <div>
      <section className="hero">
        <div className="hero-content">
          <h1>Welcome to Grand Horizon Hotel</h1>
          <p>Comfort, elegance, and warm hospitality — book your perfect stay today.</p>
          <Link to="/rooms" className="btn btn-primary">
            View Rooms
          </Link>
        </div>
      </section>

      <section className="section">
        <h2>Featured Rooms</h2>
        {error && <p className="error-text">Could not load rooms: {error}. Is the backend running on port 8080?</p>}
        <div className="room-grid">
          {rooms.map((room) => (
            <RoomCard key={room.id} room={room} />
          ))}
        </div>
      </section>

      {testimonials.length > 0 && (
        <section className="section section-alt">
          <h2>What our guests say</h2>
          <div className="testimonial-grid">
            {testimonials.map((t) => (
              <blockquote key={t.id} className="testimonial-card">
                <p>&ldquo;{t.review}&rdquo;</p>
                <footer>&mdash; {t.name}</footer>
              </blockquote>
            ))}
          </div>
        </section>
      )}
    </div>
  );
}
