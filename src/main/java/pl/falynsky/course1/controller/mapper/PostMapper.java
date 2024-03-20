package pl.falynsky.course1.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.falynsky.course1.controller.dto.PostDTO;
import pl.falynsky.course1.controller.dto.PostDtoRecord;
import pl.falynsky.course1.controller.dto.PostTitleDto;
import pl.falynsky.course1.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
    List<PostTitleDto> mapPostsToPostTitleDTOs(List<Post> posts);
    List<Post> mapPostTitleDTOsToPosts(List<PostTitleDto> posts);
    List<Post> mapPostDTOsToPosts(List<PostDTO> postsDTOs);
    List<PostDTO> mapPostsToPostDTOs(List<Post> posts);
    PostDtoRecord postToPostDtoRecord(Post post);
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "id", ignore = true)
    Post postDtoRecordToPost(PostDtoRecord post);
}
