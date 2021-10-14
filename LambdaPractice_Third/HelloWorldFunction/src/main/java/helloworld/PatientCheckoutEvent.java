package helloworld;

import com.amazonaws.annotation.SdkTestInternalApi;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientCheckoutEvent {
    private String firstName;
    private String lastName;
    private String middleName;
    private String ssn ;


}
