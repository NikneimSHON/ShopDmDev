package entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {
    private Long id;
    private String street;
    private Long idEmploy;
    private String city;
    private Long houseNumber;
    private Long apartmentHumber;



}
