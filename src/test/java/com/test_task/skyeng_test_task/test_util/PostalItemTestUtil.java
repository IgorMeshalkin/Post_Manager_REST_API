package com.test_task.skyeng_test_task.test_util;

import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.PostalItemType;

public class PostalItemTestUtil {
    public static PostalItem getPostalItemForControllerRegistrationTest() {
        PostalItem postalItem = new PostalItem();
        postalItem.setType(PostalItemType.POSTCARD);
        postalItem.setRecipientIndex(632211);
        postalItem.setRecipientAddress("Адрес тестового отправления");
        postalItem.setRecipientName("Имя получателя тестового отправления");
        return postalItem;
    }

    public static PostalItem getPostalItemForServiceArrivalMethod() {
        PostalItem result = getPostalItemForControllerRegistrationTest();
        result.setId(1L);
        return result;
    }
}
