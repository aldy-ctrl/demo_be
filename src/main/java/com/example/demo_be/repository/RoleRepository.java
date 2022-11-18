package com.example.demo_be.repository;

import com.example.demo_be.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {

}
