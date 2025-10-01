package com.mcphub.domain.member.repository.elasticsearch;

import com.mcphub.domain.member.entity.MemberElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MemberElasticSearchRepository extends ElasticsearchRepository<MemberElasticDocument,String> {
    List<MemberElasticDocument> findByEmailContaining(String keyword); // 이메일을 기준으로 키워드 검색
    List<MemberElasticDocument> findByNicknameContaining(String keyword); // 닉네임을 기준으로 키워드 검색
    List<MemberElasticDocument> findByEmailContainingOrNicknameContaining(String emailKeyword, String nicknameKeyword); // 닉네임과 이메일 둘 다 검색 조건에 들어감
}
