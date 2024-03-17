package pl.falynsky.course1.controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.falynsky.course1.controller.dto.PostDTO;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.service.PostService;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostDTO> getPosts(@RequestParam(required = false) int page) {
        int pageNumber = page > 0 ? page : 0;
        return PostDTOMapper.mapToPostDTOs(postService.getPosts(pageNumber));
    }

    @GetMapping("/posts/comments")
    public List<Post> getPostsWithComments(@RequestParam(required = false) int page) {
        int pageNumber = page > 0 ? page : 0;
        return postService.getPostsWithComments(pageNumber);
    }

    @GetMapping("/posts/title/{title}")
    public List<Post> getPosts(@PathVariable String title) {
        return postService.getPostsByTitle(title);
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable long id) {
        return postService.getPost(id);
    }
}
