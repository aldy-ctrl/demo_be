package com.example.demo_be.repository;

import com.example.demo_be.entity.RoleMenuEntity;
import com.example.demo_be.entity.RoleMenuId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMenuRepo extends JpaRepository<RoleMenuEntity, RoleMenuId> {

}
