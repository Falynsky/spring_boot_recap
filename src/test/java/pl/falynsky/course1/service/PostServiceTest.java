package pl.falynsky.course1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pl.falynsky.course1.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WithMockUser
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void shouldReturnPosts() {
        // given
        // when
        Post post = postService.getPost(1);
        // then
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(1);
    }
}