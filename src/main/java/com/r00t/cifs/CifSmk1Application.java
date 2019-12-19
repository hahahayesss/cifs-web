package com.r00t.cifs;

import com.r00t.cifs.configs.FileStorageProperties;
import com.r00t.cifs.models.UserModel;
import com.r00t.cifs.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class CifSmk1Application extends SpringBootServletInitializer implements CommandLineRunner {
    @Autowired
    private UserModelRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CifSmk1Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CifSmk1Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        UserModel userModel = new UserModel();
        userModel.setUsername("qwe");
        userModel.setPassword(new BCryptPasswordEncoder().encode("123456"));
        userModel.setRegistration(System.currentTimeMillis() + "");
        userModel.setRole("ROLE_USER");
        userModel.setActive(Boolean.TRUE);

        repository.insert(userModel);
    }
}
