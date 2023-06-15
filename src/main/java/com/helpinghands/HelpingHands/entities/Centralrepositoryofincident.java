package com.helpinghands.HelpingHands.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Centralrepositoryofincident {
    @Id
    private String id;
    @NotBlank(message = "Name must not be blank")
    private String name;


    @NotBlank(message = "District must not be blank")
    private String district;

    @NotBlank(message = "State must not be blank")
    private String State;

    @NotBlank(message = "Description must not be blank")
    private String Description;

    private long Casualty;


    @Column(name = "End_date", columnDefinition = "DATE")
    private Date incidentEndDate;


    @Column(name = "Date", columnDefinition = "DATE")
    private LocalDate incidentDate;

    @Column(name = "time", columnDefinition = "TIME")
    private LocalTime incidenttime;
}

