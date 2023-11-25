# Functional requirements
1. ~~Service should be able to create shortened url/links against a long url~~
1. ~~Click to the short URL should redirect the user to the original long URL~~
1. ~~Shortened link generated should be as small as possible~~
1. Users can create custom url with maximum character limit of 16
1. Service should collect metrics like most clicked links
1. there should be user defined custom expiry duration for each links  


# Non functional requirements
1. URL redirection should be fast and should not degrade at any point of time (Even during peak loads)
1. Service should expose REST API’s so that it can be integrated with third party applications

# Url Generators
The prime url generator implemented is ```RadixGeneraor``` this converts the decimal number to a given base using a pre-defined
```CHARACTER_SET```. 

The decimal number initially starts from 0 and each time the generator is called 
it increments the number and converts it to a string which becomes short url.

These auto-generated urls are designed to expire after a fixed interval the start counter tracks this and 
similar to a circular linked list it rounds of once ```end==max```. This way it does not need to query database to
avoid collision.

```max = pow(len(CHARACTER_SET),MAX_URL_LEN)```

This generator is configured to have approx 10^12 generated urls 
