package com.giunne.commonservice.infra.external.domain.member.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${app.apiUrl.member-service}", name = "memberInfoClient")
public interface MemberInfoClient {
}
