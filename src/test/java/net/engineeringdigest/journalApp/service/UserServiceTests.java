package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// right click here -> More Run/Debug -> Run 'test.java' with coverage
// use this to see how many lines of code you have covered
//
//@AfterEach
//public void setup(){}
//@AfterAll @AfterEach -> afterEach runs after execution of each test case and afterAll runs after the execution of All test cases
//@BeforeAll @BeforeEach -> beforeEach runs before execution of each test case and beforeAll runs before the execution of All test cases
// so they can be used to set up something if you want to before/after running each/all test case
//example create a csv run test case using that csv and after running delete the csv
@SpringBootTest // to tell that we want to start spring boot context similar to running the application
public class UserServiceTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @Disabled // use to disable running a particular while whole test file runs
    @ParameterizedTest
    @ValueSource(strings = {"Ram", "Vipul"})
    public void findByUserName(String name) {
        assertNotNull(userRepository.findByUserName(name));
        assertFalse(userRepository.findByUserName(name).getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "5,1,6"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}
