# Demonstration of Spring Security/Spring Cloud Gateway resetting Cookies on response

The application here consists of 4 parts:

* Discovery Server
* Spring Cloud Gateway API Gateway
* Spring Authorization Server
* Purchases API Resource Server

When subsequent requests are made through the API Gateway with an established session, the `SESSION` Cookie is getting 
reset in the response headers like `Set-Cookie: Session=`.

## Prerequisites:

1. Add an entry for `auth-server` to `/etc/hosts
```
127.0.0.1       localhost auth-server
```

## Steps to start:
1. Start the Discovery Server
1. Start the Auth Server
1. Start the API Gateway
1. Start the Purchases API

## Steps to reproduce
Open a browser and navigate to [Purchases API](http://localhost:8765/api/purchases)

At this point you should be redirected to the Auth Server at http://auth-server:8080/login

Sign-in with the following credentials:
* username: client1
* password: password

Upon successful login you'll be redirected back to http://localhost:8765/purchases and the response should contain an empty array `[ ]`

Open the Chrome Inspector Network Tab and refresh the page. Now inspect the Response Headers and note there is a `Set-Cookie` attribute that looks like:
```
SESSION=; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; HttpOnly; SameSite=Lax
```

This resets the session so subsequent calls trigger new sessions to be created.

## Possible Workarounds

There are 2 possible workarounds to note.

*Spring Cloud Gateway*
You can set a filter that removed the `Set-Cookie` response header

```
- RemoveResponseHeader=Set-Cookie
```

*Spring Security*
You can set the Spring Security `RequestCache` to a NoOp implementation

```
http
    .requestCache( requestCache ->
            requestCache.requestCache( NoOpServerRequestCache.getInstance() )
    )
```

Either of these 2 solutions resolves the issue.