# Hotel Reservation System — Backend (Plain Java, no frameworks)

No Spring, no Maven/Gradle, no external dependencies at all — just the JDK.
Uses `com.sun.net.httpserver.HttpServer` (built into the JDK) for the REST API
and a hand-written JSON reader/writer (`util/Json.java`).

Requires **JDK 17+** (uses `Files.readString`/`writeString`).

## Run it

From the `backend/` folder:

```bash
# compile
mkdir -p out
javac -d out $(find src/main/java -name "*.java")

# run (starts on http://localhost:8080)
java -cp out com.hotel.Main
```

Or on Windows (PowerShell):

```powershell
mkdir out
Get-ChildItem -Recurse -Filter *.java src/main/java | ForEach-Object { $_.FullName } | Out-File sources.txt
javac -d out "@sources.txt"
java -cp out com.hotel.Main
```

You can also open the `backend/` folder directly in IntelliJ IDEA or VS Code
(with the Java extension) and run `Main.java` — no project/build file needed.

## Endpoints

| Method | Path                  | Description                          |
|--------|-----------------------|---------------------------------------|
| GET    | /api/rooms             | List all rooms                        |
| GET    | /api/rooms/{id}        | Get a single room                     |
| GET    | /api/testimonials      | List testimonials                     |
| GET    | /api/reservations      | List all reservations                 |
| POST   | /api/reservations      | Create a reservation                  |

POST body example:

```json
{
  "name": "Abeer Burman",
  "phone": "9876543210",
  "email": "abeer@example.com",
  "checkIn": "2026-10-01",
  "checkOut": "2026-10-04",
  "adults": 2,
  "children": 0,
  "note": "Late check-in please",
  "roomId": 2
}
```

## Data

- Rooms and testimonials are seeded in memory (`store/DataStore.java`) — edit
  that file to change the sample data.
- Reservations are persisted to `reservations.json`, written next to wherever
  you run the server from. No database is used.

## Project layout

```
backend/
  src/main/java/com/hotel/
    Main.java                 entry point, starts the HTTP server
    model/                    Room, Reservation, Testimonial
    store/DataStore.java      in-memory data + file persistence
    handler/                  one HttpHandler per REST resource
    util/Json.java            dependency-free JSON parse/serialize
```
