package com.tinho.OpenIdDemo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class MainView extends VerticalLayout {
    private static final String LOGOUT_SUCCESS_URL = "/";
    public MainView(UserSession userSession) {
        User user = userSession.getUser();
        Button logoutButton = new Button("Logout", click -> {
            UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(
                    VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                    null);
        });
        add(new H1("Hello %s!".formatted(user.getFirstName())));
        add(new Paragraph("Your email is %s".formatted(user.getEmail())));
        add(new Image(user.getPicture(), "User Image"));
        add(logoutButton);
        setAlignItems(Alignment.CENTER);
    }
}