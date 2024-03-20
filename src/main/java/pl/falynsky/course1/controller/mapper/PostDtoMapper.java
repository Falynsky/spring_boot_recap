package pl.falynsky.course1.controller.mapper;

import pl.falynsky.course1.controller.dto.PostTitleDto;
import pl.falynsky.course1.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDtoMapper {

    public static List<PostTitleDto> mapPostToPostTitleDto(List<Post> posts) {
        return posts.stream()
                .map(post -> new PostTitleDto(post.getId(), post.getTitle()))
                .collect(Collectors.toList());
    }
}
