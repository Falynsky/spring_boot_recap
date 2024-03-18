package pl.falynsky.course1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.repository.PostRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    void shouldGetSinglePost1() throws Exception {
        // given
        Post newPost = new Post();
        newPost.setTitle("test");
        newPost.setContent("test");
        postRepository.save(newPost);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/post/" + newPost.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // then
        Post post = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post.class);
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(newPost.getId());
        assertThat(post.getTitle()).isEqualTo("test");

    }

    @Test
    void shouldGetSinglePost2() throws Exception {
        // given

        // when
        MvcResult mvcResult = mockMvc.perform(get("/post/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //.andExpect(jsonPath("$.id").value(1));

        // then
        Post post = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post.class);
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(1);
        assertThat(post.getTitle()).isEqualTo("Hello World");
        assertThat(post.getComments().size()).isEqualTo(2);

    }
}
