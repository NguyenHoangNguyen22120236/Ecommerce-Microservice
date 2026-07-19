package com.ecommercemicroservice.user.repository;

import com.ecommercemicroservice.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
