package com.test_task.skyeng_test_task.test_util;

import com.test_task.skyeng_test_task.entities.entities.PostOffice;

public class PostOfficeTestUtil {
    public static PostOffice getPostOfficeForServiceArrivalMethod() {
        PostOffice postOffice = new PostOffice();
        postOffice.setId(1L);
        postOffice.setName("Имя тестового отделения почты");
        postOffice.setAddress("Адрес тестового отделения почты");
        return postOffice;
    }
}
