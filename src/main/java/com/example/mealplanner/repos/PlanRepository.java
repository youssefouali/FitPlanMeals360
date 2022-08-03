package com.example.mealplanner.repos;

import com.example.mealplanner.entity.Plan;
import com.example.mealplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("select p from Plan p where p.user = ?1 AND p.startDate <= ?2 AND p.endDate >= ?2")
    Plan findByUserIdAndStartDateBeforeAndEndDateAfter(User user, LocalDate today);

    List<Plan> findAllByUserId(long userId);

    List<Plan> findAllByUserIdAndEndDateGreaterThanEqual(long userId, LocalDate date);

    List<Plan> findAllByUserIdAndEndDateLessThan(long userId, LocalDate date);
}
