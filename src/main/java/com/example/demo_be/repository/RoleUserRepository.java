package com.example.demo_be.repository;

import com.example.demo_be.entity.RoleUserEntity;
import com.example.demo_be.entity.RoleUserId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<RoleUserEntity, RoleUserId> {

}
