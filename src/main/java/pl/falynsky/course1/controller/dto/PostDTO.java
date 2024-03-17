package pl.falynsky.course1.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDTO {
    private long id;
    private String title;
    private String content;
}
