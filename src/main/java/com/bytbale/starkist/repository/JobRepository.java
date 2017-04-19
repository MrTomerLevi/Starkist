package com.bytbale.starkist.repository;

import com.bytbale.starkist.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Integer> {

    Optional<Job> findById(Integer id);

    Optional<Job> findByName(String name);

    @Transactional
    List<Job> deleteByName(String name);
}

