package uk.gov.digital.ho.hocs.info.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "nominated_person")
@NoArgsConstructor
@AllArgsConstructor
public class NominatedPerson {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Column(name = "teamUUID")
    UUID teamUUID;

    @Getter
    @Column(name = "email_address")
    String emailAddress;
}