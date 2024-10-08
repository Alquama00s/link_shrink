# LinkShrink

<!-- ![LinkShrink Logo](linkshrink-logo.png) -->

LinkShrink is a simple and efficient URL shortening service that allows you to shorten long URLs into concise, easy-to-share links. With LinkShrink, you can create, manage, and track your shortened URLs quickly and conveniently.

## Table of Contents

- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Usage](#usage)
- [Wiki](https://github.com/Alquama00s/link_shrink/wiki)
- [Contributing](https://github.com/Alquama00s/link_shrink/wiki/Roadmap)
- [License](./LICENSE)

## Getting Started

### Installation

[//]: # (LinkShrink can be used both through our web platform or via an API. To use the web platform, simply visit [linkshrink.com]&#40;https://www.linkshrink.com&#41; and follow the on-screen instructions to shorten your URLs.)

[//]: # ()
[//]: # (If you prefer to use our API, follow these steps:)

[//]: # ()
[//]: # (1. Sign up for a LinkShrink API account at [api.linkshrink.com]&#40;https://api.linkshrink.com/signup&#41;.)

[//]: # (2. Obtain your API key from your account dashboard.)

[//]: # (3. Use the API key to authenticate your requests when using LinkShrink API.)

The backend is being written on spring boot and can be started by ```mvn spring-boot:run```

### Usage

[//]: # (#### Web Platform)

[//]: # ()
[//]: # (1. Visit [linkshrink.com]&#40;https://www.linkshrink.com&#41;.)

[//]: # (2. Create an account or log in.)

[//]: # (3. Enter the long URL you want to shorten in the provided field.)

[//]: # (4. Click the "Shorten" button.)

[//]: # (5. Your shortened URL will be generated and ready to use.)

#### API

You can use LinkShrink API to programmatically shorten URLs. Example using cURL:

```bash
curl -X POST http://localhost:8080/api/urls/create \
     -H "Content-Type: application/json" \
     -d '{
         "longUrl": "https://example.com/your-long-url"
     }'



# metrics
# http_server_requests_seconds_sum{job="redirectorms",uri="/{shortUrl}"}/http_server_requests_seconds_count{job="redirectorms",uri="/{shortUrl}"}