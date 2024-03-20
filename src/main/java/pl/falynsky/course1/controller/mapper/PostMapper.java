package pl.falynsky.course1.controller.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.falynsky.course1.controller.dto.PostDTO;
import pl.falynsky.course1.controller.dto.PostDtoRecord;
import pl.falynsky.course1.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);

    PostDtoRecord postToPostDtoRecord(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "id", ignore = true)
    Post postDtoRecordToPost(PostDtoRecord post);
}
