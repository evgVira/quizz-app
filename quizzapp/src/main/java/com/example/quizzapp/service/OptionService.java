package com.example.quizzapp.service;

import com.example.quizzapp.storage.entity.Option;

import java.util.List;
import java.util.Set;

public interface OptionService {

    void saveOptionsForQuestion(List<Option> options);

    List<Option> getOptionsByQuestionIds(Set<String> questionIds);

    Option getOptionById(String optionId);

    void removeOptionsByQuestionIds(Set<String> questionIds);

}
