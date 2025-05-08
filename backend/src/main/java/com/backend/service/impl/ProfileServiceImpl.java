package com.backend.service.impl;

import com.backend.persistence.entity.TipEntity;
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

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TipRepository tipRepository;

    public ProfileDTO getProfileByUserId(Long id){

        UserOutputDTO userDto = userRepository.findOutputUserById(id);
        
        List<TipEntity> tipsSent = tipRepository.findTipsSentByUserId(id);
        List<TipEntity> tipsReceived = tipRepository.findTipsReceivedByUserId(id);

        userDto.setTipsSent(tipsSent);
        userDto.setTipsReceived(tipsReceived);

        List<PostOutputDTO> posts = postRepository.findPostsByUserId(id);



        //this is for testing
        List<ActivityOutputDTO> activityData = List.of(
            new ActivityOutputDTO("Mar", 67)
        );



        return ProfileDTO.builder()
            .user(userDto)
            .activityData(activityData)
            .posts(posts)
            .build();


    }

}