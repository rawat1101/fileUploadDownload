package csv;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Car {
    public static final int ststus = 1;
    @CsvBindAndSplitByName(column = "type", required = true, elementType = Integer.class, splitOn = "-")
    private List<String> types;
    @CsvBindByName
    private int id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", types=" + types +
                '}';
    }
}
