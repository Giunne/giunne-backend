package com.giunne.questservice.domain.roadMap.application;

import com.giunne.questservice.domain.roadMap.application.dto.request.GetIRoadmapRequestDto;
import com.giunne.questservice.domain.roadMap.application.dto.response.GetIRoadmapResponseDto;
import com.giunne.questservice.domain.roadMap.application.interfaces.RoadMapRepository;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadMapService {
    private final RoadMapRepository roadMapRepository;

     public List<GetIRoadmapResponseDto> findByRecreation(GetIRoadmapRequestDto dto){
        List<RoadMap> roadMapList = roadMapRepository.findByRecreation(dto);
         return roadMapList.stream().map(i -> GetIRoadmapResponseDto.builder()
                 .id(i.getId())
                 .title(i.getTitle().getValue())
                 .description(i.getDescription().getValue())
                 .roadMapType(i.getRoadMapType())
                 .build()).toList();
    }

    public List<GetIRoadmapResponseDto> findAll(){
        List<RoadMap> roadMapList = roadMapRepository.findAll();
        return roadMapList.stream().map(i -> GetIRoadmapResponseDto.builder()
                .id(i.getId())
                .title(i.getTitle().getValue())
                .description(i.getDescription().getValue())
                .roadMapType(i.getRoadMapType())
                .build()).toList();
    }

}
