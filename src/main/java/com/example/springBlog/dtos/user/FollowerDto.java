package com.example.springBlog.dtos.user;

import com.example.springBlog.dtos.post.PostGetDto;
import com.example.springBlog.entities.Follower;
import com.example.springBlog.entities.Post;
import com.example.springBlog.entities.enums.FollowStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FollowerDto {
    private Long id;
    private String username;
    private LocalDate dateFollow;
    private FollowStatus statusFollow;

    public static FollowerDto toDto(Follower follower) {
        FollowerDto followerDto = new FollowerDto();
        followerDto.setId(follower.getUserFollower().getId());
        followerDto.setUsername(follower.getUserFollower().getUsername());
        followerDto.setStatusFollow(follower.getStatusFollow());
        followerDto.setDateFollow(follower.getDateFollow());
        return followerDto;
    }

    public static List<FollowerDto> toListDto(List<Follower> followersFromDb) {
        List<FollowerDto> followers = followersFromDb.stream().map(FollowerDto::toDto)
                .collect(Collectors.toList());
        return followers;

    }

}
