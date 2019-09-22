package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

}
