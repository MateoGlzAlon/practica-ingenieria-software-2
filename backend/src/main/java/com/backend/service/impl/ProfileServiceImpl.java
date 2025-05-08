package com.backend.service.impl;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.CommentEntity;
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

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;


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
        - make code for ActivityData

        - change links -> database
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
                .date("published on "+DateTimeFormatter.ofPattern("d-M-yyyy").withZone(ZoneId.systemDefault())
                    .format(p.getCreatedAt().toInstant())) //format for making -> 10-3-2024
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
                .date(DateTimeFormatter.ofPattern("d-M-yyyy").withZone(ZoneId.systemDefault())
                    .format(te.getCreatedAt().toInstant()))
                //.date(te.getCreatedAt())
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
                .date(DateTimeFormatter.ofPattern("d-M-yyyy").withZone(ZoneId.systemDefault())
                    .format(te.getCreatedAt().toInstant()))
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


        

        List<CommentEntity> commentsUser = commentRepository.findByUserId(id);

        //update
        Map<String,Integer> stats = Map.of(
            "questions", posts.size(),
            "answers",   commentsUser.size()
        );


        UserOutputDTO userDto = UserOutputDTO.builder()
            .id(userData.getId())
            .name("UPDATE-DB-FROM-BACKEND")
            .username(userData.getUsername())
            .role("UPDATE-DB-FROM-BACKEND")
            .email(userData.getEmail())
            .about(userData.getAbout())
            .avatarUrl(userData.getAvatarUrl())
            .memberSince(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH).withZone(ZoneId.systemDefault())
                .format(userData.getCreatedAt().toInstant())) //format for making -> April 2021
            .tipsSent(tipsSentTotal)
            .tipsReceived(tipsReceivedTotal)
            .links(links)
            .stats(stats)
            .build();
        
        


        //this is for testing
        /*List<ActivityOutputDTO> activityData = List.of(
            new ActivityOutputDTO("Mar", 67)
        );*/


        List<Object[]> postCounts = postRepository.findPostCountsByMonth(id);
        List<Object[]> commentCounts = commentRepository.findCommentCountsByMonth(id);
        String[] months = { "Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" };
        

        Map<String,Integer> totals = new LinkedHashMap<>();

        for (String m : months) {
            totals.put(m, 0);
        }


        for (Object[] row : postCounts) {
            String month = (String) row[0];
            int cnt = ((Number) row[1]).intValue();

            totals.put(month, totals.getOrDefault(month, 0) + cnt);
        }


        for (Object[] row : commentCounts) {
            String month = (String) row[0];
            int cnt = ((Number) row[1]).intValue();

            totals.put(month, totals.getOrDefault(month, 0) + cnt);
        }



        List<ActivityOutputDTO> activityData = new ArrayList<>();

        for (String m : months) {

            ActivityOutputDTO aDataDTO = ActivityOutputDTO.builder()
                .month(m)
                .contributions(totals.get(m))
                .build();


            activityData.add(aDataDTO);
        }


        return ProfileDTO.builder()
            .user(userDto)
            .activityData(activityData)
            .posts(posts_total)
            .build();


    }

}