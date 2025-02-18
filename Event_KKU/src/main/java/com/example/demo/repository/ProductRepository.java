package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Event;

public interface ProductRepository extends JpaRepository<Event, Integer> {

	List<Event> findByIsActiveTrue();

	Page<Event> findByIsActiveTrue(Pageable pageable);

	List<Event> findByCategory(String category);

	List<Event> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2);

	Page<Event> findByCategory(Pageable pageable, String category);

	Page<Event> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2, Pageable pageable);

	Page<Event> findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);

	@Query("SELECT e FROM Event e WHERE (LOWER(e.title) LIKE LOWER(CONCAT('%', :ch, '%')) OR LOWER(e.category) LIKE LOWER(CONCAT('%', :ch2, '%'))) AND (:startDateTime IS NULL OR e.startdate >= :startDateTime) AND (:endDateTime IS NULL OR e.enddate <= :endDateTime)")
	Page<Event> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndDateRange(@Param("ch") String ch,
			@Param("ch2") String ch2, @Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime, Pageable pageable);
}
