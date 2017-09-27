package purdue.bowlingapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginUnitTest {
    @Test
    public void login_correct() throws Exception {
        assertEquals(MainActivity.isValidLogin(true), true);
    }

    @Test
    public void login_incorrect_pass() throws Exception {
        assertEquals(MainActivity.isValidLogin(false), false);
    }

    @Test
    public void login_incorrect_username() throws Exception {
        assertEquals(MainActivity.isValidLogin(false), false);
    }

    @Test
    public void login_unSanitized() throws Exception {
        assertEquals(MainActivity.isValidLogin(false), false);
    }
}