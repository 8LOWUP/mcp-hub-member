package com.mcphub.domain.member.service.member;

import com.mcphub.domain.member.dto.request.MemberModifyRequest;
import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.entity.MemberElasticDocument;
import com.mcphub.domain.member.repository.elasticsearch.MemberElasticSearchRepository;
import com.mcphub.domain.member.repository.querydsl.MemberDslRepository;
import com.mcphub.global.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.mcphub.domain.member.status.MemberErrorStatus.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberDslRepository memberDslRepository;
    private final MemberElasticSearchRepository memberElasticSearchRepository;

    @Override
    public void existById(Long memberId) {
        if (!memberDslRepository.existById(memberId)) throw new RestApiException(MEMBER_NOT_FOUND);
    }

    public Member findById(Long memberId) throws UsernameNotFoundException {
        return memberDslRepository.findByIdNotFetchLoginInfo(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
    }

    @Override
    public Member findByIdNotFetchLoginInfo(Long memberId) {
        return memberDslRepository.findByIdNotFetchLoginInfo(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
    }

    @Override
    public List<MemberElasticDocument> findByEmailContainingOrNicknameContaining(String keyword) {
        return memberElasticSearchRepository.findByEmailContainingOrNicknameContaining(keyword, keyword);
    }

    @Override
    public Boolean modifyMemberProfile(Long id, MemberModifyRequest request) {
        existById(id); // 실제로 유저 있는지 확인
        if (!Objects.equals(id, request.getId())) throw new RestApiException(MEMBER_ACCESS_DENIED);
        if (!memberDslRepository.modifyMember(request)) throw new RestApiException(MEMBER_UPDATE_ERROR);
        return true;
    }

    @Override
    public MemberElasticDocument saveMemberToElasticSearch(MemberRM member) {
        if (memberElasticSearchRepository.existsById(member.id().toString())) {
            return null;
        }

        return memberElasticSearchRepository.save(
                new MemberElasticDocument(member.id().toString(), member.email(), member.nickname())
        );
    }
}
