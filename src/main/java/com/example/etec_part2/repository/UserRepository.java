package com.example.etec_part2.repository;

import com.example.etec_part2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



}
