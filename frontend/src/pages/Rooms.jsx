import { useEffect, useState } from "react";
import { api } from "../api/client";
import RoomCard from "../components/RoomCard";

export default function Rooms() {
  const [rooms, setRooms] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .getRooms()
      .then(setRooms)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="section">
      <h1>Our Rooms</h1>
      <p className="section-subtitle">Choose from our range of comfortable rooms and suites.</p>

      {loading && <p>Loading rooms...</p>}
      {error && <p className="error-text">Could not load rooms: {error}. Is the backend running on port 8080?</p>}

      <div className="room-grid">
        {rooms.map((room) => (
          <RoomCard key={room.id} room={room} />
        ))}
      </div>
    </div>
  );
}
