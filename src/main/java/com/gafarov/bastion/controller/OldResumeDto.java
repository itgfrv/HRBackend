package com.gafarov.bastion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OldResumeDto{
        @JsonProperty("phone_number")
        private String phoneNumber;
        private String email;
        @JsonProperty("military_duty")
        private String militaryDuty;
        private String education;
        @JsonProperty("metro_station")
        private String metroStation;
        @JsonProperty("good_qualities")
        private String goodQualities;
        @JsonProperty("bad_qualities")
        private String badQualities;
        @JsonProperty("bad_habits")
        private String badHabits;
        @JsonProperty("reasons_for_working")
        private String reasonsForWorking;
        @JsonProperty("good_job_qualities")
        private String goodJobQualities;
        private String busyness;
        @JsonProperty("resume_src")
        private String resumeSrc;
}
