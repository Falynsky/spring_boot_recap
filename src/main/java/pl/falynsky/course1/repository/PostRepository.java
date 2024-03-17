package pl.falynsky.course1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.falynsky.course1.model.Post;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitle(String title);

    @Query("select distinct p from Post p")
    List<Post> findAllPosts(Pageable page);
}
