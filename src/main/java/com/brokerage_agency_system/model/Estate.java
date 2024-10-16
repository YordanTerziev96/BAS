package com.brokerage_agency_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "estates")
public class Estate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "estate_id")
    private List<Image> images;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(columnDefinition = "TEXT")
    private String coordinates;

    @Column(nullable = true)
    private List<String> comments;

    @Column(nullable = false)
    private Long price;

    @Override
    public String toString() {
        return "Estate{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", price=" + price +
                ", comments=" + comments +
                '}';
    }
}


