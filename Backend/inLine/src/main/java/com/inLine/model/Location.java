package com.inLine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class Location {

    @Schema(description = "Street name.",
            example = "Main Street",
            name = "street",
            required = true)
    @NotBlank
    @Column(name = "street_name")
    private String streetName;

    @Schema(description = "House/building number.",
            example = "7001",
            name = "house_number",
            required = true)
    @Positive
    @Column(name = "house_number")
    private int houseNumber;

    @Schema(description = "Apartment number.",
            example = "8",
            name = "apt",
            nullable = true)
    @Nullable
    @Column(name = "apt_number")
    private Integer aptNumber;

    @Schema(description = "Country.",
            example = "United States",
            name = "country",
            required = true)
    @NotBlank
    @Column(name = "country")
    private String country;

    @Schema(description = "City.",
            example = "Miami",
            name = "city",
            required = true)
    @NotBlank
    @Column(name = "city")
    private String city;

    @Schema(description = "State",
            example = "FL",
            name = "state",
            required = true)
    @Column(name = "state")
    @NotBlank
    private String state;

    @Schema(description = "Zip code.",
            example = "81509",
            name = "zip",
            required = true)
    @Digits(integer = 5, fraction = 0)
    @Positive
    @Column(name = "zip_code")
    private int zip;

    public Location(@JsonProperty("street") String streetName,
                    @JsonProperty("house_number") int houseNumber,
                    @JsonProperty("apt") Integer aptNumber,
                    @JsonProperty("country") String country,
                    @JsonProperty("city") String city,
                    @JsonProperty("state") String state,
                    @JsonProperty("zip") int zip)
    {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.aptNumber = aptNumber;
        this.country = country;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
