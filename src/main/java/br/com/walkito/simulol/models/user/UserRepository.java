package br.com.walkito.simulol.models.user;

import br.com.walkito.simulol.models.gameSession.GameSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ '$or': [ { 'username': ?0 }, { 'email': ?0 } ] }")
    User findByUsernameOrEmail(String userEmail);
}
