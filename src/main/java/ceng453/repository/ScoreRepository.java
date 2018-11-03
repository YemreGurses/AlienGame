package ceng453.repository;

import ceng453.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    Score findByScore(Integer score);

}