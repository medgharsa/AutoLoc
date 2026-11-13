package tn.esprit.autoloc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Column(nullable = false)
    private LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private boolean valide;

    // 1 Contrat <-> 1 Reservation
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    // 1 Contrat -> N Paiement (cascade ALL : suppression en cascade)
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements;
}