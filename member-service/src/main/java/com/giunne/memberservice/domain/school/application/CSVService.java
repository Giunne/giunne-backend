package com.giunne.memberservice.domain.school.application;


import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.school.api.request.SchoolCsvDto;
import com.giunne.memberservice.domain.school.domain.School;
import com.giunne.memberservice.domain.school.interfaces.SchoolRepository;
import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CSVService {

    private final int BATCH_SIZE = 100;
    private final SchoolRepository schoolRepository;

    @Transactional
    public void schoolCsvToEntity(MultipartFile file) {
        if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".csv")) {
            throw new BusinessException(ErrorCode.INVALID_SCHOOL);
        }

        try {

            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

            CsvToBean<SchoolCsvDto> csvToBean = new CsvToBeanBuilder<SchoolCsvDto>(reader)
                    .withType(SchoolCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<SchoolCsvDto> parse = csvToBean.parse();
            List<School> schoolCsvDtoList = parse.stream()
                    .map(SchoolCsvDto::toSchool)
                    .collect(Collectors.toList());

            List<List<School>> resultList = new ArrayList<>();
            int listSize = schoolCsvDtoList.size();
            for (int i = 0; i < listSize; i += BATCH_SIZE) {
                int toIndex = Math.min(i + BATCH_SIZE, listSize);
                List<School> sublist = schoolCsvDtoList.subList(i, toIndex);
                processBatch(sublist);
            }

//            for (List<School> schoolList : resultList) {
////                schoolRepository.saveAll(schoolList);
//                processBatch(schoolList);
//            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_FORMAT);
        }
    }

    private void processBatch(List<School> schoolList) {
        List<String> schoolIdList = schoolList.stream().map(item -> item.getSchoolId().getSchoolId())
                .toList();
        List<School> updateList = schoolRepository.findBySchoolIdList(schoolIdList);
        List<String> updateIdList = updateList.stream().map(item -> item.getSchoolId().getSchoolId()).toList();

        List<School> insertList = schoolList.stream().filter(item -> !updateIdList.contains(item.getSchoolId().getSchoolId())).toList();
        schoolRepository.saveAll(insertList);
        schoolRepository.updateSchool(updateList);

    }
}
