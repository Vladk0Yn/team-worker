package com.teamworker.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Position {

    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_name")
    private String name;

    //todo change one to many to many to many
    @OneToMany(mappedBy = "position")
    private List<User> users;
}
