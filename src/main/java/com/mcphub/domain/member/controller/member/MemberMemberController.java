 package com.mcphub.domain.member.controller.member;

 import com.mcphub.domain.member.adviser.member.MemberAuthAdviser;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.Parameter;
 import io.swagger.v3.oas.annotations.Parameters;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import jakarta.servlet.http.HttpServletRequest;
 import lombok.RequiredArgsConstructor;
 import org.springframework.web.bind.annotation.*;
 import com.mcphub.global.common.base.BaseResponse;

 @Tag(name = "일반 사용자(App)용 Member API", description = "일반 사용자(App)용 Member 관련 API")
 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/members")
 public class MemberMemberController {

     private final MemberAuthAdviser memberAuthAdviser;

     @Operation(summary = "회원 탈퇴 API", description = "해당 유저 정보를 삭제하는 API입니다.")
     @ApiResponses({
             @ApiResponse(
                     responseCode = "200",
                     description = "회원탈퇴 성공"
             )
     })
     @Parameters({
             @Parameter(name = "refreshToken", description = "로그인시 받는 refreshToken"),
     })
     @DeleteMapping("/me")
     public BaseResponse<Boolean> withdrawal(
             HttpServletRequest request,
             @RequestParam String refreshToken
     ) {
         return BaseResponse.onSuccess(memberAuthAdviser.withdrawal(request, refreshToken));
     }
 }
