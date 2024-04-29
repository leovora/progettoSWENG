package com.progettosweng.application.views.helloworld;

import com.progettosweng.application.service.UserService;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.isNull;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HelloWorldView extends HorizontalLayout {

    private TextField username;
    private Button sayHello;

    @Autowired
    private UserService userService;

    public HelloWorldView() {
        username = new TextField("Your email");
        sayHello = new Button("Insert email");
        sayHello.addClickListener(e -> {

            User user = userService.getUser(username.getValue());
            if(isNull(user)){
                Notification.show("User not found");
            }else{
                Notification.show("Hello " + user.getNome() + " " + user.getCognome());
            }

        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, username, sayHello);

        add(username, sayHello);
    }

}
