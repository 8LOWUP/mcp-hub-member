package com.mcphub.domain.member.service.member;

import com.mcphub.domain.member.dto.request.MemberModifyRequest;
import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.entity.MemberElasticDocument;

import java.util.List;

public interface MemberService {
    void existById(Long memberId);

    Member findById(Long memberId);

    Member findByIdNotFetchLoginInfo(Long memberId);

    List<MemberElasticDocument> findByEmailContainingOrNicknameContaining(String keyword);

    Boolean modifyMemberProfile(Long id, MemberModifyRequest request);

    MemberElasticDocument saveMemberToElasticSearch(MemberRM member);
}
