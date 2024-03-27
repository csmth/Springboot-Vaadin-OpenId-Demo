package com.tinho.OpenIdDemo;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class WelcomeView extends VerticalLayout{
    private static final String OAUTH_URL = "/oauth2/authorization/my-oidc-client";
    public WelcomeView(@Autowired Environment env) {
        String clientkey = env.getProperty("spring.security.oauth2.client.registration.my-oidc-client.client-id");
        setPadding(true);
        setAlignItems(Alignment.CENTER);

        // Check that oauth keys are present
        if (clientkey == null || clientkey.isEmpty() || clientkey.length() < 32) {
            add(new Paragraph(
                "Could not find OAuth client key in application.properties. "
                + "Please double-check the key and refer to the README.md file for instructions.")
            );
        } else {
            add(new H1("Login to access this app"));
            add(new Paragraph("This is TinHo's demo app for Spring Security + developer OpenID. Please Login here:"));
            Anchor loginLink = new Anchor(OAUTH_URL, "Login with OpenID");
            loginLink.addClassName(LumoUtility.FontSize.XLARGE);
            loginLink.setRouterIgnore(true); // actually navigate away from this app
            add(loginLink);
        }
    }
}
