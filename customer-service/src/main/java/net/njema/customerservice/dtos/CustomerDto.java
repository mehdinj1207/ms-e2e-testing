package net.njema.customerservice.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class CustomerDto {
    private Long id;
    @NotEmpty
    @Size(min=2)
    private String firstName;
    @NotEmpty
    @Size(min=2)
    private String lastName;
    @NotEmpty
    @Size(min=8)
    private String email;
}
