package hu.morabarna.csaladfa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @RequiredArgsConstructor @EqualsAndHashCode
@Table(name = "parents_children")
public class ParentsChildren {
    public ParentsChildren(Person parent, Person child) {
        this.parent=parent;
        this.child=child;
        this.id=null;
    }

    //Purpose: ONLY a JOINTABLE related repository and service only exists to serve the PersonService or Person Repository!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Person parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Person child;

}
