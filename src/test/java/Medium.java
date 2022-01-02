import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Medium {
    String baseUrl = "https://api.medium.com";
    String token = "20340059625a07132d27268384f62c3e07455d265fbb62cb052c384332a4e4a1a";
    String name = "test";
    String username = "seleniumtester102";
    String userId = "154b1928931e32d04fe8a9029d543b3df46628d4c6f9a04b7fe42524d4994814b";

    @Test
    void getUser(){
        String requestUrl = baseUrl + "/v1/me";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
        when().
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String responseName = response.path("data.name");
        String responseUsername = response.path("data.username");

        Assert.assertEquals(responseName,name);
        Assert.assertEquals(responseUsername,username);
    }

    @Test
    void getPublications(){
        String requestUrl = baseUrl + "/v1/users/" + userId + "/publications";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
        when().
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        int i=0;
        while (true){
            String publicationName = response.path("data["+i+"].name");
            String publicationId = response.path("data["+i+"].id");
            i++;
            if (publicationName != null){
                System.out.println(i + " - " + publicationName + " - " + publicationId);
            }
            else break;
        }
    }

    @Test
    void createPost(){
        String requestUrl = baseUrl + "/v1/users/" + userId + "/posts";

        JSONObject request = new JSONObject();

        request.put("title","Api Test");
        request.put("contentFormat","html");
        request.put("content","<h1> Api Test with Medium </h1><p> Rest Assured api test.</p>");

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

        String postTitle = response.path("data.title").toString();
        String authorId = response.path("data.authorId").toString();

        Assert.assertEquals(postTitle,"Api Test");
        Assert.assertEquals(authorId,userId);
    }

}
