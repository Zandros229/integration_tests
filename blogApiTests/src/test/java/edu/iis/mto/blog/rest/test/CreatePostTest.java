package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class CreatePostTest extends FunctionalTests {

    private static final String VALID_POST_API = "/blog/user/1/post";
    private static final String INVALID_POST_API = "/blog/user/2/post";

    @Test
    public void creatingAPostWithValidDataShouldReturnCreated() {
        JSONObject jsonObj = new JSONObject().put("entry", "Test");

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(VALID_POST_API);
    }

    @Test
    public void creatingAPostForNonConfirmedUserShouldReturnBadRequest() {
        JSONObject jsonObj = new JSONObject().put("entry", "Test");

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CONFLICT)
                .when()
                .post(INVALID_POST_API);
    }
}