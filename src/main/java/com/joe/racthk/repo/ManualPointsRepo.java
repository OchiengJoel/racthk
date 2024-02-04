package com.joe.racthk.repo;

import com.joe.racthk.model.ManualPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualPointsRepo extends JpaRepository<ManualPoints, Long> {
}
