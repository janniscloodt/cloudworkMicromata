package com.oskarwiedeweg.cloudwork.feed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePostDto {

    @NotBlank
    @Pattern(regexp = "([a-zA-Z0-9. ?,-]).{5,30}")
    private final String title;

    @Pattern(regexp = "([a-zA-Z0-9. ?,-]).{0,500}")
    private final String description;

}
