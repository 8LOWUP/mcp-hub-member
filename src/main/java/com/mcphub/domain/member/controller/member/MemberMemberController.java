 package com.mcphub.domain.member.controller.member;

 import com.mcphub.domain.member.adviser.member.MemberAuthAdviser;
 import com.mcphub.domain.member.dto.request.MemberModifyRequest;
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

     @Operation(summary = "프로필 조회 API", description = "요청을 보낸 유저의 프로필을 조회하는 API입니다.")
     @ApiResponses({
             @ApiResponse(
                     responseCode = "200",
                     description = "프로필 조회 성공"
             )
     })
     @GetMapping("/me")
     public BaseResponse<?> getUMemberProfile() {
         return null;
     }

     @Operation(summary = "프로필 수정 API", description = "요청을 보낸 유저의 프로필을 수정하는 API입니다.")
     @ApiResponses({
             @ApiResponse(
                     responseCode = "200",
                     description = "프로필 수정 성공"
             )
     })
     @Parameters({
             @Parameter(name = "id", description = "요청한 유저의 memberId"),
             @Parameter(name = "email", description = "요청한 유저의 이메일"),
             @Parameter(name = "nickname", description = "요청한 유저의 닉네임"),
     })
     @PatchMapping("/me")
     public BaseResponse<?> modifyMemberProfile(@RequestBody MemberModifyRequest request) {
         return null;
     }

     @Operation(summary = "다른 회원 프로필 조회 API", description = "다른 유저의 프로필을 조회하는 API입니다.")
     @ApiResponses({
             @ApiResponse(
                     responseCode = "200",
                     description = "프로필 조회 성공"
             )
     })
     @Parameters({
             @Parameter(name = "memberId", description = "다른 유저의 memberId (DB에 저장되는 seq 값)"),
     })
     @GetMapping("/{memberId}")
     public BaseResponse<?> getOtherMemberProfile(@PathVariable Long memberId) {
         return null;
     }

     @Operation(summary = "검색창 조회 API", description = "요청을 보낸 유저가 입력한 단어와 유사한 유저들을 조회하는 API 입니다.")
     @ApiResponses({
             @ApiResponse(
                     responseCode = "200",
                     description = "검색창 조회 성공"
             )
     })
     @Parameters({
             @Parameter(name = "keyword", description = "유저가 입력한 키워드"),
     })
     @GetMapping("/search")
     public BaseResponse<?> getMembersByKeyword(@RequestParam String keyword
     ) {
         return null;
     }
 }
