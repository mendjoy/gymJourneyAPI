package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.domain.User;
import io.github.mendjoy.gymJourneyAPI.domain.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findAllByUser(User user, Pageable pageable);

    @Query("""
                SELECT w FROM Workout w
                WHERE w.user.id = :userId
                  AND w.startDate <= :today
                  AND (w.endDate IS NULL OR w.endDate >= :today)
            """)
    Page<Workout> findActiveByUser(
            @Param("userId") Long userId,
            @Param("today") LocalDate today,
            Pageable pageable
    );

    @Query("""
                SELECT w FROM Workout w
                WHERE w.user.id = :userId
                  AND w.endDate < :today
            """)
    Page<Workout> findInactiveByUser(
            @Param("userId") Long userId,
            @Param("today") LocalDate today,
            Pageable pageable
    );

}