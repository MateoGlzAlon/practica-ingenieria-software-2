package com.backend.service.impl;

import com.backend.persistence.entity.CommentEntity;
import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.*;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.persistence.inputDTO.UserLinksInputDTO;
import com.backend.repository.CommentRepository;
import com.backend.repository.PostRepository;
import com.backend.repository.TipRepository;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TipRepository tipRepository;
    private final CommentRepository commentRepository;

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserInputDTO findUserInputByID(Long id) {
        return userRepository.findInputUserById(id);
    }

    public ProfileDTO getProfileByUserId(Long id){

        List<PostEntity> posts = postRepository.findPostsByUserId(id);
        List<PostOutputDTO> postsTotal = new ArrayList<>();

        for(PostEntity p : posts){
            PostOutputDTO nPost = PostOutputDTO.builder()
                    .id(p.getId())
                    .title(p.getTitle())
                    .votes(p.getVotes())
                    .answers((long)p.getComments().size())
                    .date(p.getCreatedAt())
                    .build();

            postsTotal.add(nPost);
        }

        UserEntity userData = userRepository.findById(id).orElse(null);

        List<TipEntity> tipsSent = tipRepository.findTipsSentByUserId(id);
        List<TipEntity> tipsReceived = tipRepository.findTipsReceivedByUserId(id);

        List<TipSentOutputDTO> tipsSentTotal = new ArrayList<>();

        for(TipEntity te : tipsSent){
            TipSentOutputDTO nTip = TipSentOutputDTO.builder()
                    .receiver(te.getReceiver().getUsername())
                    .amount(te.getAmount())
                    .date(te.getCreatedAt())
                    .build();
            tipsSentTotal.add(nTip);
        }

        List<TipReceivedOutputDTO> tipsReceivedTotal = new ArrayList<>();

        for(TipEntity te : tipsReceived){
            TipReceivedOutputDTO nTip = TipReceivedOutputDTO.builder()
                    .sender(te.getSender().getUsername())
                    .amount(te.getAmount())
                    .date(te.getCreatedAt())
                    .build();
            tipsReceivedTotal.add(nTip);
        }

        List<CommentEntity> commentsUser = commentRepository.findByUserId(id);

        Map<String,Integer> stats = Map.of(
                "questions", posts.size(),
                "answers",   commentsUser.size()
        );


        if (userData == null) return null;

        UserOutputDTO userDto = UserOutputDTO.builder()
                .id(userData.getId())
                .name(userData.getName())
                .username(userData.getUsername())
                .role(userData.getRole())
                .email(userData.getEmail())
                .about(userData.getAbout())
                .avatarUrl(userData.getAvatarUrl())
                .memberSince(userData.getCreatedAt())
                .tipsSent(tipsSentTotal)
                .tipsReceived(tipsReceivedTotal)
                .githubLink(userData.getGithub_link())
                .twitterLink(userData.getTwitter_link())
                .websiteLink(userData.getWebsite_link())
                .stats(stats)
                .build();


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
                .posts(postsTotal)
                .build();


    }

    public UserEntity changeUserLinks(UserLinksInputDTO userLinks){

        UserEntity user = userRepository.findById(userLinks.getUserId()).get();

        user.setGithub_link(userLinks.getGithub_link());
        user.setWebsite_link(userLinks.getWebsite_link());
        user.setTwitter_link(userLinks.getTwitter_link());

        return userRepository.save(user);

    }

    public Long getUserIdByEmail(String email){
        return userRepository.findByEmail(email).get().getId();
    }
    
}
