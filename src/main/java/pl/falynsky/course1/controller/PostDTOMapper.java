package pl.falynsky.course1.controller;

import pl.falynsky.course1.controller.dto.PostDTO;
import pl.falynsky.course1.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDTOMapper {

    private PostDTOMapper() {
    }

    public static List<PostDTO> mapToPostDTOs(List<Post> posts) {
        return posts.stream()
                .map(PostDTOMapper::mapToPostDTO)
                .collect(Collectors.toList());
    }

    private static PostDTO mapToPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

}
