package steps;

import Helpers.HttpHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;

public class GitHubSteps {
    String baseURL = "https://api.github.com";
    String username = "MustafaSelimG";
    String token = "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";
    Response response;

    @Given("send get request for user")
    public void sendGetRequestForUser() {
        String requestURI = baseURL + "/user";

        response = HttpHelper.getRequest(requestURI, token);
    }

    @Then("verify status code for success")
    public void verifyStatusCodeForSuccess() {
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @And("verify response body for get user")
    public void verifyResponseBodyForGetUser() {
        String name = response.path("name").toString();
        String email = response.path("email").toString();
        String id = response.path("id").toString();

        Assert.assertEquals(name, "Mustafa Selim Gunaydin");
        Assert.assertEquals(email, "mustafaselimgunaydin@gmail.com");
        Assert.assertEquals(id, "88919177");
    }

    @Given("send get request for repositories")
    public void sendGetRequestForRepositories() {
        String requestURI = baseURL + "/users/" + username + "/repos";

        response = HttpHelper.getRequest(requestURI, token);
    }

    @And("verify response body for get repositories")
    public void verifyResponseBodyForGetRepositories() {
        int i = 0;
        while (true) {
            String repo = response.path("[" + i + "].name");
            String name = response.path("[" + i + "].owner.login");
            i++;
            if (repo != null) {
                Assert.assertEquals(name, "MustafaSelimG");
                System.out.println(i + " - " + repo);
            } else break;
        }
    }

    @Given("send get request for repository")
    public void sendGetRequestForRepository() {
        String repoName = "MustafaSelimG";
        String requestURI = baseURL + "/repos/" + username + "/" + repoName;

        response = HttpHelper.getRequest(requestURI, token);
    }

    @And("verify response body for get repository")
    public void verifyResponseBodyForGetRepository() {
        String id = response.path("id").toString();
        String name = response.path("name").toString();
        String admin = response.path("permissions.admin").toString();

        Assert.assertEquals(id, "410197376");
        Assert.assertEquals(name, "MustafaSelimG");
        Assert.assertEquals(admin, "true");
    }

    @Given("send get request for readme")
    public void sendGetRequestForReadme() {
        String repoName = "MustafaSelimG";
        String requestURI = baseURL + "/repos/" + username + "/" + repoName + "/readme";

        response = HttpHelper.getRequest(requestURI, token);
    }

    @And("verify response body for get readme")
    public void verifyResponseBodyForGetReadme() {
        String path = response.path("path");
        Assert.assertEquals(path, "README.md");
    }

    @Given("send post request for repository")
    public void sendPostRequestForRepository() {
        String requestURI = baseURL + "/user/repos";

        JSONObject request = new JSONObject();
        request.put("name", "test");
        request.put("auto_init", "true");
        request.put("private", "true");
        request.put("gitignore_template", "nanoc");

        response = HttpHelper.postRequest(requestURI, token, request.toJSONString());
    }

    @And("verify response body for post repository")
    public void verifyResponseBodyForPostRepository() {
        String repoName = response.path("name").toString();
        Assert.assertEquals(repoName, "test");
    }

    @Given("send delete request for repository")
    public void sendDeleteRequestForRepository() {
        String repoName = "test";
        String requestURI = baseURL + "/repos/" + username + "/" + repoName;

        response = HttpHelper.deleteRequest(requestURI, token);
    }

    @Then("verify status code for created")
    public void verifyStatusCodeForCreated() {
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);
    }

    @Then("verify status code for no content")
    public void verifyStatusCodeForNoContent() {
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_NO_CONTENT);
    }
}
