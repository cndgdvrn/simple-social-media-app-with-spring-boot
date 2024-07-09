package com.smapp.sm_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class Like implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
