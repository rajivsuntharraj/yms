package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RtlsSettings {
    private String closest_gateway;
    private String ssi_differential;
    private SiteInfo site;
    private Integer dwell_secs;
    private String min_gateway;
    private String min_ssi;
    private String no_of_samples;
}
