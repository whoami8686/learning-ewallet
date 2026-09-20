package learning_ewallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name="transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID reference;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User receiver;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Transfer() {
    }

    public Transfer(User sender, User receiver, BigDecimal amount) {
        this.reference = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
