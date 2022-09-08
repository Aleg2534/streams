import models.Car;
import models.CarMaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static final String CAR_DATA_PATH = "src/main/resources/CAR_DATA.csv";
    private static final String DELIMITER = ",";

    public static List<String> Reader() throws IOException {
        return Files.lines(Paths.get(CAR_DATA_PATH)).skip(1).filter(s -> !s.contains(",,")).collect(Collectors.toList());
    }

    public static List<Car> convertingLinesToCars(List<String[]> listLines) {
        List<Car> cars = new ArrayList<>();
        listLines.forEach(strings -> {
            Car config = Car.builder()
                    .carModel(strings[0])
                    .carModelYear(Integer.parseInt(strings[2]))
                    .color(strings[3])
                    .carMaker(new CarMaker(strings[1]))
                    .build();
            cars.add(config);
        });
        return cars;
    }

    public static List<String[]> splitLineTOStringArrs() throws IOException {
        List<String> lines = Reader();
        List<String[]> list = new ArrayList<>();
        lines.forEach(s -> list.add(s.split(DELIMITER)));
        return list;
    }

    public static Map<String, List<Car>> carMapString(List<Car> list)
    {
//        Map<String,List<Car>> mapOfCars = new HashMap<>();
//        list.forEach(car->{
//            if (mapOfCars.get(car.getColor())==null)
//            {
//                List<Car> cars = new ArrayList<>();
//                cars.add(car);
//                mapOfCars.put(car.getColor(),cars);
//            }
//            else
//            {
//                (mapOfCars.get(car.getColor())).add(car);
//            }
//        });
        return list.stream().collect(Collectors.groupingBy(Car::getColor));
    }

    public static Map<CarMaker, List<Car>> carMapCarmaker(List<Car> list)
    {
        return list.stream().collect(Collectors.groupingBy(Car::getCarMaker));
    }

    public static <T> Map<T,List<Car>> carMapTemplate(List<Car> list,Function<Car,T> lambda)
    {
        return list.stream().collect(Collectors.groupingBy(lambda));
    }

    public static <T> List<T> carMakerListFromCarMap(Map<T,List<Car>> carMap)
    {
        return new ArrayList<>(carMap.keySet());
    }

    public static List<CarMaker> carMakersFilter(Map<CarMaker,List<Car>> carMap, int number)
    {
        List<CarMaker> carMakers = new ArrayList<>(carMap.keySet());
        List<CarMaker> carMakersFilter = new ArrayList<>();
        carMakers.forEach(carMaker -> {
            if((carMap.get(carMaker)).size()>=number){
                carMakersFilter.add(carMaker);
            }
        });
        return carMakersFilter;
    }

    public static void main(String[] args) {
        try {
            for (String s : Reader()
            ) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(convertingLinesToCars(splitLineTOStringArrs()));
            System.out.println(carMakersFilter(carMapTemplate(convertingLinesToCars(splitLineTOStringArrs()),Car::getCarMaker), 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
