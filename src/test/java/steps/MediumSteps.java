package steps;

import Helpers.HttpHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;

public class MediumSteps {
    String baseURL = "https://api.medium.com";
    String token = "20340059625a07132d27268384f62c3e07455d265fbb62cb052c384332a4e4a1a";
    String name = "test";
    String username = "seleniumtester102";
    String userId = "154b1928931e32d04fe8a9029d543b3df46628d4c6f9a04b7fe42524d4994814b";
    String publicationName = "Test Automation Master";
    String publicationId = "3f3ec55f5af9";
    String responseName;
    String responseUsername;
    String responseId;
    Response response;

    @Given("send get request for user profile")
    public void sendGetRequestForUserProfile() {
        String requestURI = baseURL + "/v1/me";

        response = HttpHelper.getRequest(requestURI, token);
    }

    @Then("assert status code for success")
    public void assertStatusCodeForSuccess() {
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @And("assert response body for get user profile")
    public void assertResponseBodyForGetUserProfile() {
        responseName = response.path("data.name");
        responseUsername = response.path("data.username");

        Assert.assertEquals(responseName, name);
        Assert.assertEquals(responseUsername, username);
    }

    @Given("send get request for publications")
    public void sendGetRequestForPublications() {
        String requestURI = baseURL + "/v1/users/" + userId + "/publications";

        response = HttpHelper.getRequest(requestURI, token);
    }

    @And("assert response body for get publications")
    public void assertResponseBodyForGetPublications() {
        responseName = response.path("data[0].name");
        responseId = response.path("data[0].id");

        Assert.assertEquals(responseName, publicationName);
        Assert.assertEquals(responseId, publicationId);
    }

    @Given("send post request for create post")
    public void sendPostRequestForCreatePost() {
        String requestURI = baseURL + "/v1/users/" + userId + "/posts";

        JSONObject request = new JSONObject();

        request.put("title","Api Test");
        request.put("contentFormat","html");
        request.put("content","<h1> Api Test with Medium </h1><p> Rest Assured api test.</p>");

        response = HttpHelper.postRequest(requestURI,token,request.toJSONString());
    }

    @Then("assert status code for created")
    public void assertStatusCodeForCreated() {
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
    }

    @And("assert response body for create post")
    public void assertResponseBodyForCreatePost() {
        String postTitle = response.path("data.title").toString();
        String authorId = response.path("data.authorId").toString();

        Assert.assertEquals(postTitle,"Api Test");
        Assert.assertEquals(authorId,userId);
    }
}
