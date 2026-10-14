# Hotel Reservation System — Frontend (React, JavaScript)

Plain React app (Vite + JavaScript, no TypeScript) that talks to the Java
backend over REST at `http://localhost:8080/api`.

## Run it

```bash
npm install
npm run dev
```

This starts the dev server (usually at http://localhost:5173). Make sure the
backend is running on port 8080 first (see `../backend/README.md`), or the
Rooms/Home pages will show a "could not load rooms" message.

To change the backend URL, edit `src/api/client.js`.

## Build for production

```bash
npm run build
```

Output goes to `dist/`, which you can serve with any static file host.

## Pages

- `/` — Home, hero + featured rooms + testimonials
- `/rooms` — full room listing
- `/reservation` — booking form (POSTs to `/api/reservations`)
- `/about`, `/contact` — static info pages

## Project layout

```
frontend/
  src/
    api/client.js       fetch wrapper for the backend REST API
    components/         Navbar, Footer, RoomCard
    pages/               Home, Rooms, Reservation, About, Contact
    App.jsx              routes (react-router-dom)
    index.css            all styling
```
