package br.com.walkito.simulol.models.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record User(
   @Id int id,
   String user,
   String password,
   String email,
   boolean emailConfirmed
) {}
