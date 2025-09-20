package com.mcphub.domain.member.repository.querydsl.impl;

import com.mcphub.domain.member.dto.request.MemberModifyRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.entity.QMember;
import com.mcphub.domain.member.repository.querydsl.MemberDslRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Repository
@Slf4j
@AllArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long memberId) {
        return jpaQueryFactory
                .selectFrom(qMember)
                .where(qMember.id.eq(memberId))
                .fetchFirst() != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByIdNotFetchLoginInfo(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .where(qMember.id.eq(memberId))
                        .fetchFirst()
        );
    }

    @Override
    @Transactional
    public Boolean modifyMember(MemberModifyRequest request) {
        return jpaQueryFactory.update(qMember)
                .set(qMember.email, request.getEmail())
                .set(qMember.nickname, request.getNickname())
                .where(qMember.id.eq(request.getId()))
                .execute()
                > 0;
    }
}