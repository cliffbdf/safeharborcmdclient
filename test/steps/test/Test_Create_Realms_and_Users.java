package test;

import cucumber.api.Format;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.json.*;

import static test.Utils.*;

public class Test_Create_Realms_and_Users extends TestBase {
	
	String realm4AdminUserId = "realm4admin";
	String realm4AdminPswd = "RealmPswd";
	String realm4AdminUserName = "realm 4 Admin Full Name";
	
	String realm4Id;
	String user4AdminRealms;
	String[] responses;

	@Given("^that I am not logged into SafeHarbor$")
	public void that_I_am_not_logged_into_SafeHarbor() throws Throwable {
		
	}
	
	@When("^I call CreateRealmAnon$")
	public void i_call_CreateRealmAnon() throws Throwable {
		process = makeRequest("CreateRealmAnon", "realm4", "realm 4 Org",
			realm4AdminUserId, realm4AdminUserName, "realm4admin@gmail.com",
			realm4AdminPswd);
		process.waitFor(2, java.util.concurrent.TimeUnit.SECONDS);
		responses = Utils.getResponse(process);
		if (process.exitValue() != 0) {
			throw new Exception(responses[0] + "; " + responses[1]);
		}
	}

	@Then("^the CreateRealmAnon HTTP response code should be (\\d+)$")
	public void the_CreateRealmAnon_HTTP_response_code_should_be(int expected) throws Throwable {
		
		// Returns UserDesc, which contains:
		// Id string
		// UserId string
		// UserName string
		// RealmId string

		JSONObject jSONObject;
		try {
			jSONObject = new JSONObject(responses[0]);
		} catch (Exception ex) {
			throw new Exception("stdout=" + responses[0] + ", stderr=" + responses[1], ex);
		}
		
		Object obj = jSONObject.get("Id");
		assertThat(obj instanceof String, responses[0]);
		String userObjId = (String)obj;
		
		obj = jSONObject.get("UserId");
		assertThat(obj instanceof String, responses[0]);
		String userId = (String)obj;
		
		obj = jSONObject.get("UserName");
		assertThat(obj instanceof String, responses[0]);
		String userName = (String)obj;
		
		obj = jSONObject.get("RealmId");
		assertThat(obj instanceof String, responses[0]);
		String realmId = (String)obj;
		
		assertThat(userId.equals(realm4AdminUserId));
		assertThat(userName.equals(realm4AdminUserName));
	}
}
