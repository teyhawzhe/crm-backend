package com.crm.repository.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.Users;

@Repository
public interface UsersHRepository extends JpaRepository<Users, String> {

}
