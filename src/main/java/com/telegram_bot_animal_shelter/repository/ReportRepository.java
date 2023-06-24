package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Interface ReportRepository
 * @author Zhitar Vlad
 * @version 1.0.0
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}