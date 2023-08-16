package com.example.newApi.repository;

import com.example.newApi.contract.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User2, String> {

    public User2 findByUserId(String userId);
    public User2 findByEmail(String email);

}
