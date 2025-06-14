package com.backend.persistence.outputdto;

import com.backend.persistence.entity.PostEntity;
import com.backend.persistence.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentOutputDTO {
        
        private Long id;
        private String authorProfilePicture;
        private String author;
        private String content;
        private long authorId;
        private int votes;
        private Date createdAt;
        private boolean accepted;

}
