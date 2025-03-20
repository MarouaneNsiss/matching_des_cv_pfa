package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private long fileSize;

    @Lob
    private byte[] fileContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
