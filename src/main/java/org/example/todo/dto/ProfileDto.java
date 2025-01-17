package org.example.todo.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private Long profileId;
    private String email;
    private String imgId;
    private Integer point;
    private String name;
}
