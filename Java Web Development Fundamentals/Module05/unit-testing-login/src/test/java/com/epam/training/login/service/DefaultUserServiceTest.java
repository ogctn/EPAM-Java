package com.epam.training.login.service;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DefaultUserServiceTest {

    private UserStore userStoreTest;
    private DefaultUserService userServiceTest;

    @BeforeEach
    void setup () {
        userStoreTest = mock(UserStore.class);
        userServiceTest = new DefaultUserService(userStoreTest);
    }

    private static User user(String loginName, String password, boolean locked, Address address) {
        User u = new User();
        u.setLoginName(loginName);
        u.setPassword(password);
        u.setLocked(locked);
        u.setAddress(address);
        return u;
    }

    private static Address address(String name) {
        Address a = new Address();
        a.setName(name);
        a.setZipCode("00000");
        a.setCity("City");
        a.setAddressLine("AddressLine");
        a.setCountry("Country");
        return a;
    }

    @Nested
    @DisplayName("login()")
    class LoginTests {

        @Test
        @DisplayName("successful login returns SUCCESS and resets failed counter")
        void login_successful_returnsSuccess_andResetsCounter() {
            User user = user("adam", "secret_adam", false, address("Adam Davis"));
            when(userStoreTest.getUserByLoginName("adam")).thenReturn(user);
            LoginResult result = userServiceTest.login("adam", "secret_adam");

            assertEquals(LoginResult.SUCCESS, result);

            verify(userStoreTest, times(1)).getUserByLoginName("adam");
            verify(userStoreTest, times(1)).updateFailedLoginCounter("adam", 0);
            verify(userStoreTest, never()).getFailedLoginCounter(anyString());
            verifyNoMoreInteractions(userStoreTest);
        }

        @Test
        @DisplayName("unsuccessful login with already locked user throws UserLockedException")
        void login_lockedUser_throwsUserLockedException() {
            User lockedUser = user("adam", "secret_adam", true, address("Adam Davis"));
            when(userStoreTest.getUserByLoginName("adam")).thenReturn(lockedUser);
            UserLockedException ex = assertThrows(
                    UserLockedException.class,
                    () -> userServiceTest.login("adam", "secret_adam")
            );

            assertEquals("User is locked", ex.getMessage());

            verify(userStoreTest, times(1)).getUserByLoginName("adam");
            verify(userStoreTest, never()).getFailedLoginCounter(anyString());
            verify(userStoreTest, never()).updateFailedLoginCounter(anyString(), anyInt());
            verifyNoMoreInteractions(userStoreTest);
        }

        @Test
        @DisplayName("existing user + wrong password returns UNSUCCESSFUL and increments failed counter")
        void login_existingUser_wrongPassword_returnsUnsuccessful_andIncrementsCounter() {
            User user = user("adam", "secret_adam", false, address("Adam Davis"));
            when(userStoreTest.getUserByLoginName("adam")).thenReturn(user);
            when(userStoreTest.getFailedLoginCounter("adam")).thenReturn(0);
            LoginResult result = userServiceTest.login("adam", "wrong");

            assertEquals(LoginResult.UNSUCCESSFUL, result);

            verify(userStoreTest, times(1)).getUserByLoginName("adam");
            verify(userStoreTest, times(1)).getFailedLoginCounter("adam");
            verify(userStoreTest, times(1)).updateFailedLoginCounter("adam", 1);
            verifyNoMoreInteractions(userStoreTest);
            assertFalse(user.isLocked(), "User must remain unlocked when failed attempts < 3");
        }

        @Test
        @DisplayName("existing user + wrong password locks user on 3rd failed attempt and throws UserLockedException")
        void login_existingUser_wrongPassword_locksUserOnThirdAttempt_andThrows() {
            User user = user("adam", "secret_adam", false, address("Adam Davis"));
            when(userStoreTest.getUserByLoginName("adam")).thenReturn(user);
            when(userStoreTest.getFailedLoginCounter("adam")).thenReturn(2);

            UserLockedException ex = assertThrows(
                    UserLockedException.class,
                    () -> userServiceTest.login("adam", "wrong")
            );
            assertEquals("User is locked", ex.getMessage());

            verify(userStoreTest, times(1)).getUserByLoginName("adam");
            verify(userStoreTest, times(1)).getFailedLoginCounter("adam");
            verify(userStoreTest, times(1)).updateFailedLoginCounter("adam", 3);
            verifyNoMoreInteractions(userStoreTest);

            assertTrue(user.isLocked(), "User must be locked when failed attempts reach 3");
        }

        @Test
        @DisplayName("non-existing user returns UNSUCCESSFUL and does not touch counters")
        void login_nonExistingUser_returnsUnsuccessful() {
            when(userStoreTest.getUserByLoginName("oguz")).thenReturn(null);

            LoginResult result = userServiceTest.login("oguz", "whatever");

            assertEquals(LoginResult.UNSUCCESSFUL, result);

            verify(userStoreTest, times(1)).getUserByLoginName("oguz");
            verifyNoMoreInteractions(userStoreTest);
        }

        @Test
        @DisplayName("getLoggedInUserAddress() returns address of authenticated user after successful login")
        void getLoggedInUserAddress_afterSuccessfulLogin_returnsAddress() {
            Address address = address("Charlotte Monet");
            User user = user("charlotte", "secret_charlotte", false, address);
            when(userStoreTest.getUserByLoginName("charlotte")).thenReturn(user);

            LoginResult result = userServiceTest.login("charlotte", "secret_charlotte");
            Address actual = userServiceTest.getLoggedInUserAddress();

            assertEquals(LoginResult.SUCCESS, result);
            assertEquals(address, actual);

            verify(userStoreTest, times(1)).getUserByLoginName("charlotte");
            verify(userStoreTest, times(1)).updateFailedLoginCounter("charlotte", 0);
            verifyNoMoreInteractions(userStoreTest);
        }
    }

}
