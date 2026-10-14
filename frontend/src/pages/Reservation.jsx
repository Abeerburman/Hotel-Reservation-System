import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { api } from "../api/client";

const initialForm = {
  name: "",
  phone: "",
  email: "",
  checkIn: "",
  checkOut: "",
  adults: 1,
  children: 0,
  note: "",
  roomId: "",
};

export default function Reservation() {
  const [searchParams] = useSearchParams();
  const [rooms, setRooms] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [status, setStatus] = useState({ state: "idle", message: "" });

  useEffect(() => {
    api.getRooms().then(setRooms).catch(() => {});
  }, []);

  useEffect(() => {
    const roomId = searchParams.get("roomId");
    if (roomId) setForm((f) => ({ ...f, roomId }));
  }, [searchParams]);

  function update(field, value) {
    setForm((f) => ({ ...f, [field]: value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setStatus({ state: "loading", message: "" });
    try {
      const payload = {
        ...form,
        adults: Number(form.adults) || 1,
        children: Number(form.children) || 0,
        roomId: Number(form.roomId) || 0,
      };
      const saved = await api.createReservation(payload);
      setStatus({
        state: "success",
        message: `Booking confirmed! Reservation #${saved.id} for ${saved.name}.`,
      });
      setForm(initialForm);
    } catch (err) {
      setStatus({ state: "error", message: err.message });
    }
  }

  return (
    <div className="section narrow">
      <h1>Make a Reservation</h1>
      <p className="section-subtitle">Fill in your details and we'll confirm your booking.</p>

      <form className="reservation-form" onSubmit={handleSubmit}>
        <div className="form-row">
          <label>
            Full Name *
            <input required value={form.name} onChange={(e) => update("name", e.target.value)} />
          </label>
          <label>
            Phone
            <input value={form.phone} onChange={(e) => update("phone", e.target.value)} />
          </label>
        </div>

        <label>
          Email *
          <input
            type="email"
            required
            value={form.email}
            onChange={(e) => update("email", e.target.value)}
          />
        </label>

        <div className="form-row">
          <label>
            Check-in *
            <input
              type="date"
              required
              value={form.checkIn}
              onChange={(e) => update("checkIn", e.target.value)}
            />
          </label>
          <label>
            Check-out *
            <input
              type="date"
              required
              value={form.checkOut}
              onChange={(e) => update("checkOut", e.target.value)}
            />
          </label>
        </div>

        <div className="form-row">
          <label>
            Adults
            <input
              type="number"
              min="1"
              value={form.adults}
              onChange={(e) => update("adults", e.target.value)}
            />
          </label>
          <label>
            Children
            <input
              type="number"
              min="0"
              value={form.children}
              onChange={(e) => update("children", e.target.value)}
            />
          </label>
        </div>

        <label>
          Room
          <select value={form.roomId} onChange={(e) => update("roomId", e.target.value)}>
            <option value="">Select a room</option>
            {rooms.map((room) => (
              <option key={room.id} value={room.id} disabled={!room.available}>
                {room.name} — ₹{room.price}/night {!room.available ? "(unavailable)" : ""}
              </option>
            ))}
          </select>
        </label>

        <label>
          Special Requests
          <textarea rows="3" value={form.note} onChange={(e) => update("note", e.target.value)} />
        </label>

        <button className="btn btn-primary" type="submit" disabled={status.state === "loading"}>
          {status.state === "loading" ? "Booking..." : "Confirm Reservation"}
        </button>

        {status.state === "success" && <p className="success-text">{status.message}</p>}
        {status.state === "error" && <p className="error-text">{status.message}</p>}
      </form>
    </div>
  );
}
