package com.linkshrink.redirector.utils.token;

import java.time.Duration;
import java.time.Instant;

public class TokenManager {

    String token;
    Instant expiry;
    Duration expiryDuration;
    JWTAuthenticator jwtAuthenticator;

}
