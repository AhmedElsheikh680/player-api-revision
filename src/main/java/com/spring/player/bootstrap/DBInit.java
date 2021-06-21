package com.spring.player.bootstrap;


import com.spring.player.model.Authorities;
import com.spring.player.model.User;
import com.spring.player.repo.AuthoritiesRepo;
import com.spring.player.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {

    private UserRepo userRepo;
    private AuthoritiesRepo authoritiesRepo;
    private PasswordEncoder passwordEncoder;

    public DBInit(UserRepo userRepo, AuthoritiesRepo authoritiesRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authoritiesRepo = authoritiesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = this.userRepo.findByEmail("kareem.ayman@yahoo.com");
//        System.out.println(user.getAddress());
        userRepo.deleteAll();
        User admin = new User("ahmed.elsheikh@yahoo.com", passwordEncoder.encode("ahmed123"), "25", "Cairo", 1);
        admin.setAuthorities(authoritiesRepo.findAll());
        userRepo.save(admin);
        ////////////////////////////////////////////////
        User manager = new User("yasser.ali@yahoo.com", passwordEncoder.encode("yasser123"), "40", "Tanata", 1);

        Authorities managerAuthorities1 = authoritiesRepo.findById(2L).get();
        Authorities managerAuthorities2 = authoritiesRepo.findById(3L).get();
        Authorities managerAuthorities3 = authoritiesRepo.findById(5L).get();
        Authorities managerAuthorities4 = authoritiesRepo.findById(6L).get();


        manager.getAuthorities().add(managerAuthorities1);
        manager.getAuthorities().add(managerAuthorities2);
        manager.getAuthorities().add(managerAuthorities3);
        manager.getAuthorities().add(managerAuthorities4);
        userRepo.save(manager);
        ///////////////////////////////////////////////
        User user = new User("kareem.ayman@yahoo.com", passwordEncoder.encode("kareem123"), "50", "Giza", 1);

        Authorities userAuthorities = authoritiesRepo.findById(3L).get();
        Authorities userAuthorities2 = authoritiesRepo.findById(6L).get();

        user.getAuthorities().add(userAuthorities);
        user.getAuthorities().add(userAuthorities2);
        userRepo.save(user);

    }

}
