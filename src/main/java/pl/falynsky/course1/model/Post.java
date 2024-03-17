package pl.falynsky.course1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    private long id;
    private String title;
    private String content;

    @OneToMany
    @JoinColumn(name = "postId")
    private List<Comment> comments;
}
