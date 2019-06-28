package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;

public class PostLikeTest extends FunctionalTests {

    private static final String OWN_POST_LIKE = "/blog/user/1/like/1";
    private static final String OWN_POST = "/blog/user/1/post";

    private static final String OTHER_POST_LIKE = "/blog/user/1/like/2";
    private static final String ANOTHER_POST_LIKE = "/blog/user/3/like/1";

    private static final String POST_LIKE_BY_NEW_USER = "/blog/user/2/like/1";
    private static final String POST_LIKE_BY_REMOVED_USER = "/blog/user/4/like/1";

    @Test
    public void likingPPostByNotConfirmedUsersShouldReturnBadRequestTest(){
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_BY_NEW_USER);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_BY_REMOVED_USER);
    }

    @Test
    public void likingPostByAnotherUserShouldReturnOkTest() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(OTHER_POST_LIKE);
    }

    @Test
    public void likeOwnPostShouldReturnBadRequestTest() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(OWN_POST_LIKE);
    }



    @Test
    public void likingSamePostTwiceShouldNotChangePostStatusTest() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(ANOTHER_POST_LIKE);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(ANOTHER_POST_LIKE);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .get(OWN_POST)
                .then()
                .body("likesCount", hasItem(1));
    }
}