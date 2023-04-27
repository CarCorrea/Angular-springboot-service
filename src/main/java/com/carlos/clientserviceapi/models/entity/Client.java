package com.carlos.clientserviceapi.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty(message = " name must not be empty")
    @Size(min = 2, max = 15, message = " must be at least 2 characters long")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = " last name must not be empty")
    @Size(min = 2, max = 15, message = " must be at least 2 characters long")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = " email must not be empty")
    @Email(message = " must be a valid email adress")
    @Column(nullable = false, unique = false, length = 100)
    private String email;

    @NotNull
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String profilePic;

    private static final long serialVersionUID = 1L;
}
