package com.mcphub.domain.member.repository.redis;

import com.mcphub.domain.member.entity.AccessTokenBlacklist;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlacklistRepository extends CrudRepository<AccessTokenBlacklist, String> {
}
