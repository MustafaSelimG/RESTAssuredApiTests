package Helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class HttpHelper {
    public static Response getRequest(String URI, String token) {
        return given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().
                get(URI).
                then().
                time(lessThan(3000L)).
                extract().response();
    }

    public static Response postRequest(String URI, String token, String requestBody) {
        return given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(requestBody).
                when().
                post(URI).
                then().
                time(lessThan(3000L)).
                extract().response();
    }

    public static Response deleteRequest(String URI, String token) {
        return given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().
                delete(URI).
                then().
                time(lessThan(3000L)).
                extract().response();
    }

}
