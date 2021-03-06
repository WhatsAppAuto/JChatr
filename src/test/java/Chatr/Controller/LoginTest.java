package Chatr.Controller;

import Chatr.Helper.HashGen;
import Chatr.Model.ErrorMessagesValidation;
import Chatr.Model.Exceptions.UserIDException;
import Chatr.Model.User;
import Chatr.Server.Server;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class LoginTest {

	/**
	 * guarantees the server is started for every test
	 */
	@BeforeClass
	public static void start() {

		try {
			Server s = new Server(3456);
			s.start();

			Thread.sleep(500);

		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void registerValidUser() {
		User l = Login.registerUser("@randomBullshit", "test@testmail.com", "Tester", "supersecret");
		User ref = new User("Tester", "@randomBullshit", "test@testmail.com", "supersecret");
		assertEquals(ref, l);
	}

	@Test
	public void loginValidUser() {
		User l = Login.loginUser("@aMerkel", "12345");
		User ref = new User("Angela Merkel", "@aMerkel", "angela@merkel.de", "12345");
		assertEquals(ref, l);
	}

	@Test
	public void loginInvalidUser() {
		String expected = "UserID or password invalid";
		String exception = "";
		try {
			Login.loginUser("@ansxnaonoxisxoi", "asdasxasdasx");
		} catch (UserIDException e) {
			exception = e.getErrorMessage();
		}
		assertEquals(expected, exception);
	}

	@Test
	public void validateValidUser() {
		ErrorMessagesValidation errorMessagesValidation = Login.validateUser("@KlausKleber", "klaus.kleber@zdf.de", "mypassword123", "Klausi");
		String[] errormessages = new String[4];
		ErrorMessagesValidation ref = new ErrorMessagesValidation();
		ref.setErrorexisting(false);
		ref.setErrormessages(errormessages);
		assertEquals(ref, errorMessagesValidation);
	}

	@Test
	public void validateUserCompletelyInvalid() {
		ErrorMessagesValidation errorMessagesValidation = Login.validateUser("@K", "klaus.kleber", "my", "Ki");
		String[] errormessages = new String[4];
		errormessages[0] = "ID contains invalid characters.";
		errormessages[1] = "Email is invalid.";
		errormessages[2] = "Password is too short.";
		errormessages[3] = "Username is too short.";
		ErrorMessagesValidation ref = new ErrorMessagesValidation();
		ref.setErrorexisting(true);
		ref.setErrormessages(errormessages);
		assertEquals(ref, errorMessagesValidation);
	}

	@Test
	public void validateUserInvalid() {
		ErrorMessagesValidation errorMessagesValidation = Login.validateUser("Klkaslxnasx", "klaus.kleber@zdf.de", "myPassword", "IchEsseGerneButterbrote");
		String[] errormessages = new String[4];
		errormessages[0] = "ID is invalid.";
		errormessages[3] = "Username is too long.";
		ErrorMessagesValidation ref = new ErrorMessagesValidation();
		ref.setErrorexisting(true);
		ref.setErrormessages(errormessages);
		assertEquals(ref, errorMessagesValidation);
	}

	@Test
	public void validateUserAlreadyExistingUserID() {
		ErrorMessagesValidation errorMessagesValidation = Login.validateUser("@aMerkel", "Peter.Griffin@aol.de", "ILoveHagrid", "BigBubi");
		String[] errormessages = new String[4];
		errormessages[0] = "UserId is already in use.";
		ErrorMessagesValidation ref = new ErrorMessagesValidation();
		ref.setErrorexisting(true);
		ref.setErrormessages(errormessages);
		assertEquals(ref, errorMessagesValidation);
	}


}

