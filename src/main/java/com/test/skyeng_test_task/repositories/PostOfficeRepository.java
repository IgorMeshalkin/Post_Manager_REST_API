package com.test.skyeng_test_task.repositories;

import com.test.skyeng_test_task.entities.entities.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
}
