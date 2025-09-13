package com.mcphub.domain.workspace.repository.querydsl.impl;

import com.mcphub.domain.workspace.entity.Workspace;
import com.mcphub.domain.workspace.repository.querydsl.WorkspaceDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class WorkspaceDslRepositoryImpl implements WorkspaceDslRepository {

    private final MongoTemplate mongoTemplate;
}
