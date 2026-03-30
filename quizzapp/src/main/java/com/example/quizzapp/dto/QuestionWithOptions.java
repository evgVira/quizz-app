package com.example.quizzapp.dto;

import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.Question;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithOptions {

    private Question question;
    private List<Option> options;

}
