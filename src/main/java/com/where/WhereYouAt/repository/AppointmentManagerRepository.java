package com.where.WhereYouAt.repository;

import com.where.WhereYouAt.domain.AppointmentManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentManagerRepository extends JpaRepository<AppointmentManager,Long> {


    List<AppointmentManager> findAllByUserId(Long userId);

    Optional<AppointmentManager> findByUserIdAndAppointmentId(Long userId, Long appointmentId);
}