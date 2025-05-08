package com.backend.service.impl;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.ActivityOutputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.ProfileService;
import com.backend.repository.UserRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.PostRepository;
import com.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TipRepository tipRepository;

    public ProfileDTO getProfileByUserId(Long id){

        /*
        private Long id;
        private String name;
        private String username;
        private String email;
        private String about;
        private String avatarUrl;
        private Date memberSince;

        private List<TipEntity> tipsSent;
        private List<TipEntity> tipsReceived;
    
        private List<String> links; 
        private List<int> stats;
        */



        UserEntity userData = userRepository.findUserById(id);
        
        List<TipEntity> tipsSent = tipRepository.findTipsSentByUserId(id);
        List<TipEntity> tipsReceived = tipRepository.findTipsReceivedByUserId(id);

        //this is for testing
        List<String> updateLinks = List.of(
            "UPDATE_LINKS_DATABASE_FROM_BACKEND",
            "UPDATE_LINKS_DATABASE_FROM_BACKEND"
        );

        // Lista de enteros con dos valores de ejemplo
        List<Integer> exampleNumbers = List.of(
            123,
            456
        );


        UserOutputDTO userDto = UserOutputDTO.builder()
            .id(userData.getId())
            .name("UPDATE-DB-FROM-BACKEND")
            .username(userData.getUsername())
            .email(userData.getEmail())
            .about(userData.getAbout())
            .avatarUrl(userData.getAvatarUrl())
            .memberSince(userData.getCreatedAt())
            .tipsSent(tipsSent)
            .tipsReceived(tipsReceived)
            .links(updateLinks)
            .stats(exampleNumbers)
            .build();


        


        /*
        private Long id;
        private String title;
        private int votes;
        private Long answers;
        private Date date;
        */
        List<PostEntity> posts = postRepository.findPostsByUserId(id);
        List<PostOutputDTO> posts_total = new ArrayList<>();

        for(PostEntity p : posts){


            PostOutputDTO nPost = PostOutputDTO.builder()
                .id(p.getId())
                .title(p.getTitle())
                .votes(p.getLikes())
                .answers((long)p.getComments().size())
                .date(p.getCreatedAt())
                .build();

            posts_total.add(nPost);


        }
        

        //this is for testing
        List<ActivityOutputDTO> activityData = List.of(
            new ActivityOutputDTO("Mar", 67)
        );



        return ProfileDTO.builder()
            .user(userDto)
            .activityData(activityData)
            .posts(posts_total)
            .build();


    }

}