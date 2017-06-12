package com.datec.soadre.core.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.datec.soadre.core.frames.Login;
import java.awt.EventQueue;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.datec.*"})
@EnableAutoConfiguration
public class Application{

    public Application() {
        super();
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            Login ex = ctx.getBean(Login.class);
            ex.prepareAndOpenFrame();
        });
    }

}
