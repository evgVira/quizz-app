package com.example.quizzapp.service.impl;

import com.example.quizzapp.service.OptionService;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    private static final String OPTIONS_CREATE_MESSAGE = "Сохранение новых вариантов ответов в базу данных";

    @Override
    @Transactional
    public void saveOptionsForQuestion(List<Option> options) {
        optionRepository.saveAll(options);
        log.info(OPTIONS_CREATE_MESSAGE);
    }

    @Override
    public List<Option> getOptionsByQuestionIds(Set<String> questionIds) {
        return optionRepository.findOptionByQuestionIds(questionIds);
    }

    @Override
    public Option getOptionById(String optionId) {
        Optional<Option> option = optionRepository.findById(optionId);
        return option.orElse(null);
    }

    @Override
    @Transactional
    public void removeOptionsByQuestionIds(Set<String> questionIds) {
        List<Option> optionsForRemove = optionRepository.findOptionByQuestionIds(questionIds);
        optionRepository.deleteAll(optionsForRemove);
        log.info("Варианты ответов для теста были удалены");
    }
}
