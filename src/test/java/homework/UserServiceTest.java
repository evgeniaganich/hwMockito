package homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User natasha;


    @BeforeEach
    public void setUp() {
        natasha = new User("Natasha", "123");
    }

    @Test
    void getAllLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Natasha", "123")));
        userRepository.addUser(natasha);
        Assertions.assertEquals(List.of("Natasha"), userService.getAllLogins());
    }

    @Test
    void ifLoginIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password should be defined");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenCorrectUserIsAddedThenAddUserIsCalledFromRepo() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        userService.createNewUser("Natasha", "123");
        verify(userRepository)
                .addUser(any());
    }

    @Test
    void whenPasswordIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("Natasha", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Login and password should be defined");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingUserIsPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "1")));
        assertThatThrownBy(() -> userService.createNewUser("test", "1"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Such user already exists");
    }

    @Test
    void whenLoginAndPasswordMatchAutentificationIsPassed() {
        when(userRepository.getAllUsers()).thenReturn(List.of(natasha));
        userService.userAutentificationByLoginAndPassword("Natasha", "123");
        Assertions.assertTrue(userService.userAutentificationByLoginAndPassword("Natasha", "123"));
    }

}
