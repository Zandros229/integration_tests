package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

public class PostLookupTest extends FunctionalTests {

    private static final String VALID_POST_LOOKUP = "/blog/user/1/post";
    private static final String NO_POSTS_LOOKUP = "/blog/user/2/post";
    private static final String REMOVED_USER_POST_LOOKUP = "/blog/user/4/post";
    private static final String LIKED_POST_LOOKUP = "/blog/user/3/post";
    private static final String LIKE_POST = "/blog/user/1/like/2";


    @Test
    public void shouldReturnNothingWhenUserWithNoPostsTest() {
        JSONObject jsonObj = new JSONObject();

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
                .get(NO_POSTS_LOOKUP);
    }

    @Test
    public void shouldReturnBadRequestWhenRemovedUserTest() {
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
                .get(REMOVED_USER_POST_LOOKUP);

    }

    @Test
    public void shouldReturnCorrectNumberOfLikesWhenValidUserAndPostHasNoLikesTest() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("likesCount", hasItem(0))
                .when()
                .get(VALID_POST_LOOKUP);
    }

    @Test
    public void shouldReturnCorrectNumberOfLikesWhenValidUserAndPostHasLikeTest() {
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
                .post(LIKE_POST);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("likesCount", hasItem(1))
                .when()
                .get(LIKED_POST_LOOKUP);
    }
}