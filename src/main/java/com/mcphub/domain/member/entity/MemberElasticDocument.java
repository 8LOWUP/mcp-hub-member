package com.mcphub.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@Document(indexName = "member") // 인덱스 이름
public class MemberElasticDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String email;

    @Field(type = FieldType.Text)
    private String nickname;

    public MemberElasticDocument(String id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}