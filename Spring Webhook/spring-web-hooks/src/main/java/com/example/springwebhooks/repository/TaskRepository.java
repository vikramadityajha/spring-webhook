package com.example.springwebhooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springwebhooks.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
