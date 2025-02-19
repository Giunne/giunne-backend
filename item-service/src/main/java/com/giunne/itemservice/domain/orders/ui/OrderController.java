package com.giunne.itemservice.domain.orders.ui;

import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.itemservice.domain.item.application.OrderService;
import com.giunne.itemservice.domain.orders.application.dto.response.GachaTypeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "주문 관리", description = "주문 조회 및 저장")
@RestController
@RequestMapping("/v1/api/items/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/gacha-type")
    public Response<List<GachaTypeResponseDto>> getGachaTypes() {
        List<GachaTypeResponseDto> gachaTypes = orderService.getGachaTypes();
        return Response.ok(gachaTypes);
    }

//    @Operation(summary = "뽑기 주문", description = """
//            ## 기능설명
//            * 뽑기 주문
//            ---
//            """, responses = {
//            @ApiResponse(responseCode = "200", description = "성공")
//    })
//    @PostMapping("/gacha")
//    public Response<String> orderGacha(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal
//    ) {
//
//
//        return Response.ok("성공");
//    }


}
