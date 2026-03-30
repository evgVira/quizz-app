package com.example.quizzapp.mapper;

import com.example.quizzapp.dto.OptionBodyCreateDto;
import com.example.quizzapp.dto.OptionResponseDto;
import com.example.quizzapp.storage.entity.Option;
import com.example.quizzapp.storage.entity.Question;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

public class OptionMapper {

    public static List<Option> mapToOptions(List<OptionBodyCreateDto> options, Question question) {
        if (!CollectionUtils.isEmpty(options)) {
            return options.stream()
                    .map(currentOptionData -> {
                        Option newOption = new Option();
                        newOption.setId(UUID.randomUUID().toString());
                        newOption.setOptionText(currentOptionData.getOptionText());
                        newOption.setOrderIndex(currentOptionData.getOrderIndex());
                        newOption.setIsCorrect(currentOptionData.getIsCorrect());
                        newOption.setQuestion(question);
                        return newOption;
                    }).toList();
        }
        return List.of();
    }

    public static List<OptionResponseDto> mapToOptionsResponseDto(List<Option> options) {
        if (!CollectionUtils.isEmpty(options)) {
            return options.stream()
                    .map(option -> {
                        OptionResponseDto optionResponseDto = new OptionResponseDto();
                        optionResponseDto.setOptionId(option.getId());
                        optionResponseDto.setOptionText(option.getOptionText());
                        optionResponseDto.setOrderIndex(option.getOrderIndex());
                        optionResponseDto.setIsCorrect(option.getIsCorrect());
                        optionResponseDto.setQuestionId(option.getQuestion().getId());
                        return optionResponseDto;
                    }).toList();
        }
        return List.of();
    }
}
