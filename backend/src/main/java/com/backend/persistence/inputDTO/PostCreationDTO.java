package com.backend.persistence.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreationDTO {

    private String title;
    private String content;
    private String tag;

    //optional value
    private List<String> links;

}