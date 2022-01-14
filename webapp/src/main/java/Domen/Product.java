package domen;

public class Product {
    private static int counter = 1;

    private String name;
    private double price;
    private int id;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.id = counter;
        counter++;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String toString(Product product) {
        return name + " " + price;
    }

    @Override
    public int hashCode() {
        int result = 11;
        for (char symbol : name.toCharArray()) {
            result += (int) symbol * 14;
        }
        for (char number : Double.toString(price).toCharArray()) {
                result += number * 17;
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return name.equals(product.getName())
                && (price == product.getPrice());
    }
}
