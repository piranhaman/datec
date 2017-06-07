package com.datec.soadre.core.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.datec.soadre.core.frames.Login;
import java.awt.EventQueue;

@SpringBootApplication
public class Application extends Login{

    public Application() {
        super();
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            Application ex = ctx.getBean(Application.class);
            ex.prepareAndOpenFrame();
        });
    }

}
