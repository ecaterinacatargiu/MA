package com.example.server.repository;

import com.example.server.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLibraryRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
