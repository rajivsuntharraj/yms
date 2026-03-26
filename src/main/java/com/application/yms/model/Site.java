package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Site {
    @Id
    private String _id;
    private List<AddressInfo> address_info;
    private Boolean is_active;
    private String temperature_std;
    private Integer total_drop_spaces;
    private String code;
    private String site;
    private Integer total_live_spaces;
    private String site_type;
    private List<String> sub_location;
    private Boolean is_verified;
    private String uom;
    private GeoLocation geo_location;
    private String timezone;
    private RtlsSettings rtls_settings;
    private List<String> parking;
    private Integer on_time_margins_in_minutes;
    private Timeline timeline;
    private Boolean is_deleted;
    private String uuid;
    private Layout layout;
    private List<String> building;
    private List<Integer> main;
    private List<String> gate_zone;
    private List<String> dock_zone;
    private Integer no_show_margins_in_minutes;
    private TaskConfig task_config;
}
