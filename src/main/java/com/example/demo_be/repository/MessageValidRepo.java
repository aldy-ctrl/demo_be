package com.example.demo_be.repository;

import com.example.demo_be.entity.MessageValidEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageValidRepo extends JpaRepository<MessageValidEntity, String> {

}
