package ceng453.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "score_id")
    private int id;

    @Column(name = "score")
    private Integer score;

}
