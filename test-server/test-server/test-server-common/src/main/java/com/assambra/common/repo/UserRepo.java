package com.assambra.common.repo;

import com.assambra.common.entity.User;
import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository("userRepo")
public interface UserRepo extends EzyMongoRepository<Long, User> {
}
