package pl.falynsky.course1.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.falynsky.course1.model.Comment;
import pl.falynsky.course1.model.Post;
import pl.falynsky.course1.repository.CommentRepository;
import pl.falynsky.course1.repository.PostRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
@Service
public class PostService {

    public static final int PAGE_SIZE = 5;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private EntityManager entityManager;

    public PostService(
            PostRepository postRepository,
            CommentRepository commentRepository,
            EntityManager entityManager
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.entityManager = entityManager;
    }

    public List<Post> getPosts() {
        return postRepository.findAllPosts();
    }

    public List<Post> getPosts(int page, Sort.Direction sort, Boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPostFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Post> allPosts = postRepository.findAllPosts(
                PageRequest.of(
                        page, PAGE_SIZE,
                        Sort.by(sort, "id")
                )
        );
        Predicate<List<Post>> predicate = posts -> posts.size() > 2;

        if (predicate.test(allPosts)) {
            log.info("Więcej niż 2 elementy w liście postów!");
        }

        Consumer<Post> consumer = post -> log.info(post.toString());
        allPosts.forEach(consumer);

        Function<Post, String> function = Post::getTitle;
        allPosts.forEach(function::apply);

        session.disableFilter("deletedPostFilter");


        Supplier<Post> supplier = Post::new;
        allPosts.forEach(t -> supplier.get());
        
        return allPosts;
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    @Cacheable(value = "Post", key = "#id")
    public Post getPost(long id) {
        return postRepository.findById(id)
                .orElseThrow();
    }

    @Cacheable(value = "PostWithComments")
    public List<Post> getPostsWithComments(int page, Sort.Direction sort, Boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPostFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Post> allPosts = postRepository.findAllPosts(
                PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id"))
        );
        session.disableFilter("deletedPostFilter");
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

    @CachePut(value = "Post", key = "#result.id")
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

    @CacheEvict(value = "PostWithComments") //todo: wywołać można tylko z innego beana, inny aspekt
    public void clearPostWithComments() {
    }
}
