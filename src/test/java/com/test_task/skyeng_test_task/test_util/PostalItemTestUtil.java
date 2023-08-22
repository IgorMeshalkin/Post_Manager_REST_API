package com.test_task.skyeng_test_task.test_util;

import com.test_task.skyeng_test_task.entities.entities.PostalItem;
import com.test_task.skyeng_test_task.entities.entity_types.PostalItemType;

public class PostalItemTestUtil {
    public static PostalItem getPostalItemForRegistrationTest() {
        PostalItem postalItem = new PostalItem();
        postalItem.setType(PostalItemType.POSTCARD);
        postalItem.setRecipientIndex(632211);
        postalItem.setRecipientAddress("Адрес теста регистрации");
        postalItem.setRecipientName("Имя теста регистрации");
        return postalItem;
    }
}
