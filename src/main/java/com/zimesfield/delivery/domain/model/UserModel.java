package com.zimesfield.delivery.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zimesfield.delivery.adapter.rdbms.entity.Authority;
import com.zimesfield.delivery.adapter.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

@Getter
@SuperBuilder
public class UserModel extends AuditableModel {

    @Setter
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Setter
    private String login;

    @Size(max = 50)
    @Setter
    private String firstName;

    @Size(max = 50)
    @Setter
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Setter
    private String email;

    @Setter
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Setter
    private String langKey;

    @Size(max = 256)
    @Setter
    private String imageUrl;

    @JsonIgnore
    @BatchSize(size = 20)
    @Setter
    private Set<Authority> authorities = new HashSet<>();
}
