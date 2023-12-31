package com.test_task.skyeng_test_task.repositories;

import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
    PostalItem findByTrackNumber(String trackNumber);
}
