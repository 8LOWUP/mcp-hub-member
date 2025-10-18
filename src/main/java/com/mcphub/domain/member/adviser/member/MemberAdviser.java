package com.mcphub.domain.member.adviser.member;

import com.mcphub.domain.member.converter.response.MemberResponseConverter;
import com.mcphub.domain.member.dto.request.MemberModifyRequest;
import com.mcphub.domain.member.dto.response.api.MemberDetailResponse;
import com.mcphub.domain.member.service.member.MemberService;
import com.mcphub.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberAdviser {

    private final MemberResponseConverter memberResponseConverter;
    private final MemberService memberService;
    private final SecurityUtils securityUtils;

//    public List<MemberDetailResponse> getMembersByKeyword(String keyword) {
//
//        // 키워드 하나만으로 닉네임 또는 이메일의 유저 검색
//        return memberService.findByEmailContainingOrNicknameContaining(keyword)
//                .stream().map(memberResponseConverter::toMemberElasticSearchResponse).toList();
//    }

    public MemberDetailResponse getMemberById() {
        Long id = securityUtils.getUserId();
        return memberResponseConverter.toMemberDetailResponse(memberService.findById(id));
    }

    public MemberDetailResponse getOtherMemberById(Long memberId) {
        return memberResponseConverter.toMemberDetailResponse(memberService.findById(memberId));
    }

    public Boolean modifyMemberProfile(MemberModifyRequest request) {
        Long id = securityUtils.getUserId();
        return memberService.modifyMemberProfile(id, request);
    }
}
