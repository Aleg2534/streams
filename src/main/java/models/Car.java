package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @AllArgsConstructor @Builder @ToString
public class Car {
    private String carModel;
    private int carModelYear;
    private String color;
    private CarMaker carMaker;

}
