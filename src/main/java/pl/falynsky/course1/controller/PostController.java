package pl.falynsky.course1.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.falynsky.course1.controller.dto.PostDTO;
import pl.falynsky.course1.controller.dto.PostDtoRecord;
import pl.falynsky.course1.controller.dto.PostTitleDto;
import pl.falynsky.course1.controller.mapper.PostMapper;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.service.PostService;

import java.util.List;

import static pl.falynsky.course1.controller.mapper.PostDtoMapper.mapPostToPostTitleDto;

@RestController
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/posts/title")
    public List<PostTitleDto> getPostsTitles(
    ) {
        return mapPostToPostTitleDto(postService.getPosts());
    }

    @GetMapping("/posts")
    public List<PostDTO> getPosts(
            @RequestParam(required = false) Integer page,
            Sort.Direction sort,
            @AuthenticationPrincipal String user
    ) {
        int pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return PostDTOMapper.mapToPostDTOs(postService.getPosts(pageNumber, sortDirection));
    }

    @GetMapping("/posts/comments")
    public List<Post> getPostsWithComments(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return postService.getPostsWithComments(pageNumber, sortDirection);
    }

    @GetMapping("/posts/title/{title}")
    public List<Post> getPosts(@PathVariable String title) {
        return postService.getPostsByTitle(title);
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable long id) {
        return postService.getPost(id);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> addPost(@RequestBody PostDTO postDTO) {
        Post post = postService.addPost(postMapper.postDTOToPost(postDTO));
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/post/{id}")
                .buildAndExpand(post.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(post);
    }

    @PostMapping("/post/v2")
    public ResponseEntity<Post> addPost(@RequestBody PostDtoRecord postDtoRecord) {
        Post post = postMapper.postDtoRecordToPost(postDtoRecord);
        postService.addPost(post);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/post/{id}")
                .buildAndExpand(post.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(post);
    }

    @PutMapping("/post/v2/{id}")
    public Post addNewPost(@PathVariable Long id, @RequestBody PostDtoRecord postDtoRecord) {
        Post post = postMapper.postDtoRecordToPost(postDtoRecord);
        post.setId(id);
        postService.updatePost(post);
        return post;
    }

    @PutMapping("/post")
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable long id) {
        postService.deletePost(id);
    }
}
