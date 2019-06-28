package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class FindUserTest extends FunctionalTests {

    private static final String USER_BASE = "/blog/user/find?searchString=";

    @Test
    public void shouldReturnUserTest() {
        JSONObject jsonObj = new JSONObject();

        String searchString = "Steward";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", is(1))
                .when()
                .get(USER_BASE + searchString);
    }

    @Test
    public void shouldNotReturnUserTest() {
        JSONObject jsonObj = new JSONObject();

        String searchString = "XYZ";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", is(0))
                .when()
                .get(USER_BASE + searchString);
    }

    @Test
    public void shouldNotReturnRemovedUserTest() {
        JSONObject jsonObj = new JSONObject();

        String searchString = "Tom";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", is(0))
                .when()
                .get(USER_BASE + searchString);
    }
}