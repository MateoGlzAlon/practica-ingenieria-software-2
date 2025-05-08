package com.backend.service.impl;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.outputdto.PostOutputDTO;
import com.backend.persistence.outputdto.ActivityOutputDTO;
import com.backend.persistence.outputdto.TipReceivedOutputDTO;
import com.backend.persistence.outputdto.TipSentOutputDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.ProfileService;
import com.backend.repository.UserRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.CommentRepository;
import com.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TipRepository tipRepository;
    private final CommentRepository commentRepository;

    /*
    TO-DO:
        - change the date output (?)
        - change links -> database
        - make code for ActivityData
        - add: 
            - name in database (users)
            - role in database (users)
    */

    public ProfileDTO getProfileByUserId(Long id){

        /*
        private Long id;
        private String title;
        private int votes;
        private Long answers;
        private Date date;
        private String role;
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

        /*
        private Long id;
        private String name;
        private String username;
        private String email;
        private String about;
        private String avatarUrl;
        private Date memberSince;

        private List<TipSentOutputDTO> tipsSent;
        private List<TipReceivedOutputDTO> tipsReceived;
    
        private List<String> links; 
        private List<int> stats;
        */

        UserEntity userData = userRepository.findUserById(id);
        
        List<TipEntity> tipsSent = tipRepository.findTipsSentByUserId(id);
        List<TipEntity> tipsReceived = tipRepository.findTipsReceivedByUserId(id);

        List<TipSentOutputDTO> tipsSentTotal = new ArrayList<>();

        for(TipEntity te : tipsSent){

            TipSentOutputDTO nTip = TipSentOutputDTO.builder()
                .receiver(te.getSender().getUsername())
                .amount(te.getAmount())
                .date(te.getCreatedAt())
                .postId(te.getPost().getId())
                .commentId(te.getComment().getId())
                .build();

            tipsSentTotal.add(nTip);
        }


        List<TipReceivedOutputDTO> tipsReceivedTotal = new ArrayList<>();

        for(TipEntity te : tipsReceived){

            TipReceivedOutputDTO nTip = TipReceivedOutputDTO.builder()
                .sender(te.getSender().getUsername())
                .amount(te.getAmount())
                .date(te.getCreatedAt())
                .postId(te.getPost().getId())
                .commentId(te.getComment().getId())
                .build();

            tipsReceivedTotal.add(nTip);
        }


        //this is for testing
        String updateLink = "UPDATE_LINKS_DATABASE_FROM_BACKEND";

        Map<String,String> links = Map.of(
            "github",  updateLink,
            "twitter", updateLink,
            "website", updateLink
        );

        //update
        Map<String,Integer> stats = Map.of(
            "questions", posts.size(),
            "answers",   commentRepository.findByUserId(id).size()
        );


        UserOutputDTO userDto = UserOutputDTO.builder()
            .id(userData.getId())
            .name("UPDATE-DB-FROM-BACKEND")
            .username(userData.getUsername())
            .role("UPDATE-DB-FROM-BACKEND")
            .email(userData.getEmail())
            .about(userData.getAbout())
            .avatarUrl(userData.getAvatarUrl())
            .memberSince(userData.getCreatedAt())
            .tipsSent(tipsSentTotal)
            .tipsReceived(tipsReceivedTotal)
            .links(links)
            .stats(stats)
            .build();


    
        
        

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