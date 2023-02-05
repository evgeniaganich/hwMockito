package homework;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();
    User natasha;
    User pavel;
    User natasha1;

    @BeforeEach
    public void setUp() {
        natasha = new User("Natasha", "123");
        pavel = new User("Pavel", "123");
        natasha1 = new User("Natasha", "456");
    }
    @Test
    public void getEmptyUsersList() {
        Assertions.assertEquals(userRepository.getAllUsers(), Collections.EMPTY_LIST);
    }
    @Test
    public void getNotEmptyUsersList() {
        userRepository.addUser(natasha);
        Assertions.assertEquals(List.of(natasha), userRepository.getAllUsers());
    }
    @Test
    public void findUserByLoginIfSuchUserExists() {
        userRepository.addUser(natasha);
        Assertions.assertEquals(userRepository.findUserByLogin("Natasha"), natasha);
    }
    @Test
    public void findUserByLoginIfSuchUserDoesNotExist() {
        userRepository.addUser(natasha);
        Assertions.assertNull(userRepository.findUserByLogin("sergey"));
    }
    @Test
    public void findUserByLoginAndPasswordIfSuchUserExists() {
        userRepository.addUser(natasha);
        Assertions.assertEquals(userRepository.findUserByLoginAndPassword("Natasha", "123"), natasha);
    }
    @Test
    public void findUserByLoginAndPasswordIfLoginMatchesWithOneExistingAndPasswordNotMatches() {
        userRepository.addUser(natasha);
        userRepository.addUser(natasha1);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Natasha", "123"), natasha1);
    }
    @Test
    public void findUserByLoginAndPasswordIfPasswordMatchesWithOneExistingAndLoginNotMatches() {
        userRepository.addUser(natasha);
        userRepository.addUser(pavel);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Natasha", "123"), pavel);
    }
}
