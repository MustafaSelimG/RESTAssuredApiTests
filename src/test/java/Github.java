import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Github {
    String baseUrl = "https://api.github.com";
    String username = "MustafaSelimG";
    String token = "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";

    @Test
    void getUser(){
        String requestUrl = baseUrl + "/user";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String name = response.path("name").toString();
        String email = response.path("email").toString();
        String id = response.path("id").toString();

        Assert.assertEquals(name,"Mustafa Selim Gunaydin");
        Assert.assertEquals(email,"mustafaselimgunaydin@gmail.com");
        Assert.assertEquals(id,"88919177");

    }

    @Test
    void getRepos(){
        String requestUrl = baseUrl + "/users/" + username + "/repos";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        int i = 0;
        while (true){
            String repo = response.path("["+i+"].name");
            String name = response.path("["+i+"].owner.login");
            i++;
            if (repo != null){
                Assert.assertEquals(name,"MustafaSelimG");
                System.out.println( i + " - " + repo);
            }
            else break;
        }
    }


    @Test
    void getRepo(){
        String repoName = "MustafaSelimG";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName;

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String id = response.path("id").toString();
        String name = response.path("name").toString();
        String admin = response.path("permissions.admin").toString();

        Assert.assertEquals(id,"410197376");
        Assert.assertEquals(name,"MustafaSelimG");
        Assert.assertEquals(admin,"true");
    }

    @Test
    void getReadme(){
        String repoName = "MustafaSelimG";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName + "/readme";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String path = response.path("path");
        Assert.assertEquals(path,"README.md");
    }

    @Test
    void postRepo(){
        String requestUrl = baseUrl + "/user/repos";
        String name = RandomStringUtils.randomAlphabetic(5);

        JSONObject request = new JSONObject();
        request.put("name",name);
        request.put("auto_init","true");
        request.put("private","true");
        request.put("gitignore_template","nanoc");

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(request.toJSONString()).
        when().
                post(requestUrl).
        then().
                statusCode(201).
                time(lessThan(3000L)).
                extract().response();

        String repoName = response.path("name").toString();
        Assert.assertEquals(repoName,name);
    }

    @Test
    void deleteRepo(){
        String repoName = "bxVVP";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName;

        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                delete(requestUrl).
        then().
                statusCode(204).
                time(lessThan(3000L));
    }

}
