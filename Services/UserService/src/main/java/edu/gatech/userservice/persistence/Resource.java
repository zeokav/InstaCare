package edu.gatech.userservice.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_uid")
    @ToString.Exclude
    @JsonManagedReference
    private Patient ownerUid;

    @Column(name = "resource_name", nullable = false, length = 50)
    private String resourceName;

    @Column(name = "resource_qty", nullable = false)
    private Integer resourceQty;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "notes", length = 200)
    private String notes;

    @Column(name = "available", nullable = false)
    private Integer available;

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