package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before
    public void setup(){
        user = new User();
        user.setFirstName("Janusz");
        user.setLastName("Jeremi");
        user.setEmail("janusz@ge.com");
        user.setAccountStatus(AccountStatus.NEW);

        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("xyz");

        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
        repository.save(likePost);
    }

    @Test
    public void likeListShouldHaveLikedPost(){

        List<LikePost> listLikedPosts = repository.findAll();
        Assert.assertTrue(listLikedPosts.contains(likePost));
    }

    @Test
    public void likedPostListHaveSizeOne(){
        List<LikePost> listLikedPosts = repository.findAll();
        Assert.assertEquals(1,listLikedPosts.size());
    }

    @Test
    public void likedPostListContainsUser(){
        List<LikePost> listLikedPosts = repository.findAll();
        Assert.assertEquals(user,listLikedPosts.get(0).getUser());
    }

    @Test
    public void likedPostListContainBlog(){
        List<LikePost> listLikedPosts = repository.findAll();
        Assert.assertEquals(blogPost,listLikedPosts.get(0).getPost());
    }

    @Test
    public void likedPostHaveLikedPost(){
        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost,listLikedPosts.get());
    }

    @Test
    public void shouldntFindPostIfUserIsWrong(){
        User fakeUser = new User();
        fakeUser.setFirstName("Janusz");
        fakeUser.setLastName("Nowak");
        fakeUser.setEmail("nowak@ge.pl");
        fakeUser.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(fakeUser);

        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(fakeUser,blogPost);
        Assert.assertEquals(Optional.empty(),listLikedPosts);
    }

    @Test
    public void shouldntFindPostIfBlogIsWrong(){
        BlogPost fakeBlogPost = new BlogPost();
        fakeBlogPost.setEntry("qwe");
        fakeBlogPost.setUser(user);

        entityManager.persist(fakeBlogPost);
        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(user,fakeBlogPost);
        Assert.assertEquals(Optional.empty(),listLikedPosts);
    }

    @Test
    public void editGoodPost(){

        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost,listLikedPosts.get());
        likePost.getUser().setEmail("example@ge.com");
        repository.save(likePost);
        listLikedPosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost,listLikedPosts.get());
        Assert.assertEquals(1,repository.findAll().size());
    }

}