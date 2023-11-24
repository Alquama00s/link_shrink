# Functional requirements
1. ~~Service should be able to create shortened url/links against a long url~~
1. ~~Click to the short URL should redirect the user to the original long URL~~
1. ~~Shortened link should be as small as possible~~
1. Users can create custom url with maximum character limit of 16
1. Service should collect metrics like most clicked links
1. shortened url length - 16 chars
1. there should be user defined custom expiry duration for each links  


# Non functional requirements
1. URL redirection should be fast and should not degrade at any point of time (Even during peak loads)
1. Service should expose REST API’s so that it can be integrated with third party applications