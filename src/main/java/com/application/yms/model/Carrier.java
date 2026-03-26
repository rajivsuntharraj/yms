package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "carriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {
    @Id
    private String _id;

    private NotificationConfig send_inventory;
    private NotificationConfig notify_on_arrival;
    private NotificationConfig notify_on_unload;
    private NotificationConfig notify_on_exit;
    private NotificationConfig notify_on_repair;
    private NotificationConfig notify_on_load;

    private String status;
    private Map<String, Object> container_trailer_demurrage;
    private List<String> cc_all_notification_email_id;
    private String name;
    private String scac;
    private Map<String, Object> drop_trailer_detention;
    private Map<String, Object> live_trailer_detention;
    private Boolean is_cc_all_notification;
    private Timeline timeline;
    private Boolean is_deleted;
    private String uuid;
}

