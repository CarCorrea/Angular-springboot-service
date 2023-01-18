package com.carlos.clientserviceapi.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="clients")
public class Client implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Email
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;
}
