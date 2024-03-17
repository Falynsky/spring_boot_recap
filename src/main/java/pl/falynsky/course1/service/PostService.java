package pl.falynsky.course1.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.falynsky.course1.model.Comment;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.repository.CommentRepository;
import pl.falynsky.course1.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    public static final int PAGE_SIZE = 2;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(
            PostRepository postRepository,
            CommentRepository commentRepository
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getPosts(int page, Sort.Direction sort) {
        return postRepository.findAllPosts(
                PageRequest.of(
                        page, PAGE_SIZE,
                        Sort.by(sort, "id")
                )
        );
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    public Post getPost(long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public List<Post> getPostsWithComments(int page, Sort.Direction sort) {
        List<Post> allPosts = postRepository.findAllPosts(
                PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id"))
        );
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

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Post post) {
        Post editedPost = postRepository.findById(post.getId()).orElseThrow();
        editedPost.setTitle(post.getTitle());
        editedPost.setContent(post.getContent());

        return editedPost;
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
