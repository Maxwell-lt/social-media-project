package maxwell_lt.socialmediaproject.dto;

import java.math.BigDecimal;

public enum Pack {
    SMALL(100, new BigDecimal("0.99"), "Basic Pack"),
    MEDIUM(525, new BigDecimal("4.99"), "Medium Pack\n5% more likes!"), // 5% bonus!
    LARGE(1100, new BigDecimal("9.99"), "Large Pack\n10% more likes!"), // 10% bonus!
    SUPER(5750, new BigDecimal("49.99"), "Super Pack\n15% more likes!"), // 15% bonus!
    EXTREME(12500, new BigDecimal("99.99"), "BEST VALUE\nExtreme Pack\n25% more likes!"); // 25% bonus! Wow!

    private int size;
    private BigDecimal price;
    private String description;

    Pack(int size, BigDecimal price, String description) {
        this.size = size;
        this.price = price;
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
