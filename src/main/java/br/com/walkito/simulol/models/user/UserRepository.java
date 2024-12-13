package br.com.walkito.simulol.models.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, Integer> {

    @Query("{ '$or': [ { 'username': ?0 }, { 'email': ?0 } ] }")
    User findByUsernameOrEmail(@Param("userEmail") String userEmail);
}
