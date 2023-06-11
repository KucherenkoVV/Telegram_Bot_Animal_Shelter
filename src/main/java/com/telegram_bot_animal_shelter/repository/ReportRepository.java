package com.telegram_bot_animal_shelter.repository;

import com.telegram_bot_animal_shelter.model.Report;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Интерфейс ReportRepository
 * @author Zhitar Vladislav
 * @version 1.0.0
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Set<Report> findListByChatId(Long id);

    Report findByChatId(Long id);

}