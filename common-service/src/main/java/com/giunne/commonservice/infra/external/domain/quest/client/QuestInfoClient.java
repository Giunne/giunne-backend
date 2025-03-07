package com.giunne.commonservice.infra.external.domain.quest.client;

import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.commonservice.ui.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${app.apiUrl.quest-service}", name = "questInfoClient")
public interface QuestInfoClient {

    @PostMapping(value = "/quest-state")
    Response<String> savePlayerQuestStates(@RequestBody CreateQuestStateRequestDto dto);

}
