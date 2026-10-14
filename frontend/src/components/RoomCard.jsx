import { Link } from "react-router-dom";

export default function RoomCard({ room }) {
  return (
    <div className="room-card">
      <div className="room-card-image" aria-hidden="true">
        <span>{room.name.charAt(0)}</span>
      </div>
      <div className="room-card-body">
        <div className="room-card-header">
          <h3>{room.name}</h3>
          <span className={`badge ${room.available ? "badge-available" : "badge-unavailable"}`}>
            {room.available ? "Available" : "Booked"}
          </span>
        </div>
        <p className="room-price">₹{room.price} / night</p>
        <p className="room-description">{room.description}</p>
        <Link to={`/reservation?roomId=${room.id}`} className="btn btn-primary">
          Book this room
        </Link>
      </div>
    </div>
  );
}
