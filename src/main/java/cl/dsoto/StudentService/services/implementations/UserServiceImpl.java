package cl.dsoto.StudentService.services.implementations;

import cl.dsoto.StudentService.models.User;
import cl.dsoto.StudentService.repositories.UserRepository;
import cl.dsoto.StudentService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 10-12-21.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User authenticate(String username, String password) throws Exception {
        User persistedUser = userRepository.findByUsername(username);

        if(persistedUser == null) {
            throw new Exception("No existe el usuario " + username);
        }

        // User provided password to validate
        String providedPassword = password;

        // Encrypted and Base64 encoded password read from database
        String securePassword = persistedUser.getPassword();

        // Salt value stored in database
        String salt = persistedUser.getSalt();

        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);

        if(passwordMatch)
        {
            return persistedUser;
        } else {
            throw new Exception("Usuario y/o contraseña incorrectos");
        }
    }

    @Override
    public User create(String username, String password) throws Exception {

        User user = new User();
        user.setUsername(username);

        String myPassword = password;

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);

        // Print out protected password
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);

        user.setPassword(mySecurePassword);
        user.setSalt(salt);

        return userRepository.save(user);
    }

}
