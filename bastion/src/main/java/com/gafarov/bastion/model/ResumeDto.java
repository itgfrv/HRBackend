package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResumeDto (
        @JsonProperty("id")
        Integer id,
        @JsonProperty("phone_number")
        String phoneNumber,
        String email,
        @JsonProperty("military_duty")
        String militaryDuty,
        String education,
        @JsonProperty("metro_station")
        String metroStation,
        @JsonProperty("good_qualities")
        String goodQualities,
        @JsonProperty("bad_qualities")
        String badQualities,
        @JsonProperty("bad_habits")
        String badHabits,
        @JsonProperty("reasons_for_working")
        String reasonsForWorking,
        @JsonProperty("good_job_qualities")
        String goodJobQualities,
        String busyness,
        @JsonProperty("resume_src")
        String resumeSrc
){
}
