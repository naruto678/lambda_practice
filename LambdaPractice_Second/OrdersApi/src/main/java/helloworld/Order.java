package helloworld;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@ToString
public class Order{

    public int id ; 
    public String itemName; 
    public int quantity; 



}
