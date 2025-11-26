# linkshrink-frontend

The frontend for LinkShrink — built with Angular. This README gives quick dev and build instructions and explains how to connect the UI to backend services while developing locally.

## Requirements

- Node.js (16+) and npm or yarn
- Angular CLI (optional: `npm i -g @angular/cli`)

## Development server

Install dependencies and run the dev server:

```bash
cd linkshrink-frontend
npm ci
npm start          # same as `ng serve`, serves at http://localhost:4200
```

Open http://localhost:4200 to view the app.

By default the app expects the API backends to be available locally; point the environment or proxy config to the API addresses during development.

## Build (production)

```bash
npm run build      # outputs build artifacts to dist/

# The repository also includes a Dockerfile under Docker/frontend used by the one-container demo
```

## Test

```bash
npm test
```

## Connecting to backend services

When running locally, the UI expects the API endpoints to be served at the ports used by the Spring Boot services (see root README for service port mapping). When the backend runs in Docker or Kubernetes the production build is usually served by an nginx container on port 80.

## Notes

- See the top-level README for overall project architecture and quick-start instructions.
- If you'd like, I can add a `proxy.conf.json` example and environment profiles so the frontend can be pointed easily to local Spring Boot instances.
