package challenge.web.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RegisterMVCResourceCommandTest {

    private RegisterMVCResourceCommand command;

    @Before
    public void setUp() {
        command = new RegisterMVCResourceCommand();
    }

    @Test
    public void testValidateInput_AllValid() {
        Map<String, String> errors = RegisterMVCResourceCommand.validateInput("Laura", "laura@example.com");
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateInput_MissingUsername() {
        Map<String, String> errors = RegisterMVCResourceCommand.validateInput("", "laura@example.com");
        assertEquals(1, errors.size());
        assertEquals("Name is required.", errors.get("username"));
    }

    @Test
    public void testValidateInput_MissingEmail() {
        Map<String, String> errors = RegisterMVCResourceCommand.validateInput("Laura", "");
        assertEquals(1, errors.size());
        assertEquals("Email is required.", errors.get("email"));
    }

    @Test
    public void testValidateInput_InvalidEmail() {
        Map<String, String> errors = RegisterMVCResourceCommand.validateInput("Laura", "invalid-email");
        assertEquals(1, errors.size());
        assertEquals("Invalid email format.", errors.get("email"));
    }
}