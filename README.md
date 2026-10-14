# Hotel Reservation System — Java + React (separate backend/frontend)

Two independent projects, no framework glue between them — the frontend
talks to the backend purely over HTTP/JSON.

- `backend/`  — plain Java (JDK HttpServer only, **no Spring**, no Maven/Gradle)
- `frontend/` — React (Vite + JavaScript, **no TypeScript**)

## Quick start

**1. Start the backend** (terminal 1):
```bash
cd backend
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
java -cp out com.hotel.Main
```
Runs at http://localhost:8080

**2. Start the frontend** (terminal 2):
```bash
cd frontend
npm install
npm run dev
```
Runs at http://localhost:5173 (open this in your browser)

See `backend/README.md` and `frontend/README.md` for full details.
