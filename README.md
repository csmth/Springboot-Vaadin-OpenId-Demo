# Project Bootup at https://start.spring.io/
- Project: maven  (you can choose gradle, not big deal)
- Language: Java 17 with Spring Boot: 3.2.4
- Packaging: Jar (init only, war is also fine)
- Dependencies
  1. OAuth2 Client
  2. Vaadin
- Dependencies wish to be added later
  1. H2 Database
  2. JDBC API
- I am using VScode at Ubuntu but I believe other IDE like Eclipse works perfectly

# Authentication by OAuth2
Default Authentication is boring and does nothing. The project needs an OIDC provider for OAuth2 client even the development only.

## Get OIDC playground at https://openidconnect.net/
Any OIDC provider is good but it must allows *callback to localhost*. Other OIDC playground account could be:
- https://developers.google.com/identity/openid-connect/openid-connect
- Setup localhost OIDC: https://github.com/appvia/mock-oidc-user-server or https://www.keycloak.org/

Set the allowed callback URL (which is 'redirect_uri') at the OIDC provider (change your port number if needed):
```
http://localhost:8080/login/oauth2/code/my-oidc-client
```
Append the text below into ```application.properties``` for the my OIDC provide:
```
spring.security.oauth2.client.provider.my-oidc.issuer-uri: https://dev-2cbf33l86hcdfi7d.us.auth0.com/
spring.security.oauth2.client.registration.my-oidc-client.provider: my-oidc
spring.security.oauth2.client.registration.my-oidc-client.client-id: FvVEVGHAEMqwfTGCxFuCQdsTIx5Ahamd
spring.security.oauth2.client.registration.my-oidc-client.client-secret: X4r-Ox-q-nEVcQqzIRFixPaHqEGShtqEWA_nY_xdSiADoOdTEefiLoF7BpDWdnb8
spring.security.oauth2.client.registration.my-oidc-client.authorization-grant-type: authorization_code
spring.security.oauth2.client.registration.my-oidc-client.scope: openid,profile,email
```
Change ```client-id```, ```client-secret``` and ```issuer-uri``` for different OIDC provider. **Do not disclose these information for any serious project**. I disclose them only because that is just a playground.

## Add SecurityConfig to demand authentication except login url
This Security Configuration is made for Vaddin. Springboot Project without Vaadin should configure its own SecurityFilterChain.
```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {
    private static final String LOGIN_URL = "/login";
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.oauth2Login(c -> c.loginPage(LOGIN_URL).permitAll());
    }
}
```

Access to everything except LOGIN_URL will requires oauth2 authentication. Logout will be handled later.
