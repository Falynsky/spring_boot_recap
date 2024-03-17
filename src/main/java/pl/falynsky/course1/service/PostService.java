package pl.falynsky.course1.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.falynsky.course1.model.Comment;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.repository.CommentRepository;
import pl.falynsky.course1.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(
            PostRepository postRepository,
            CommentRepository commentRepository
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getPosts(int page) {
        return postRepository.findAllPosts(PageRequest.of(page, 2));
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    public Post getPost(long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public List<Post> getPostsWithComments(int page) {
        List<Post> allPosts = postRepository.findAllPosts(PageRequest.of(page, 2));
        List<Long> ids = allPosts.stream()
                .map(Post::getId)
                .toList();
        List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
        allPosts.forEach(post -> post.setComments(extractComment(comments, post.getId())));
        return allPosts;
    }

    private List<Comment> extractComment(List<Comment> comments, long id) {
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .toList();
    }
}
