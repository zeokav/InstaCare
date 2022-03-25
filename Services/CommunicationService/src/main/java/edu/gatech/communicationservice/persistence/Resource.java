package edu.gatech.communicationservice.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "resources")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false)
    private Long resId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_uid")
    @ToString.Exclude
    private Patient ownerUid;

    @Column(name = "resource_name", nullable = false, length = 50)
    private String resourceName;

    @Column(name = "resource_qty", nullable = false)
    private Integer resourceQty;

    @Column(name = "location_x", nullable = false)
    private Double locationX;

    @Column(name = "location_y", nullable = false)
    private Double locationY;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "notes", length = 200)
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resource resource = (Resource) o;
        return resId != null && Objects.equals(resId, resource.getResId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}