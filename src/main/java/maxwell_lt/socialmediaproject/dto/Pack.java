package maxwell_lt.socialmediaproject.dto;

import java.math.BigDecimal;

public enum Pack {
    SMALL(100, new BigDecimal("0.99")),
    MEDIUM(525, new BigDecimal("4.99")), // 5% bonus!
    LARGE(1100, new BigDecimal("9.99")), // 10% bonus!
    SUPER(5750, new BigDecimal("49.99")), // 15% bonus!
    EXTREME(12500, new BigDecimal("99.99")); // 25% bonus! Wow!

    private int size;
    private BigDecimal price;

    Pack(int size, BigDecimal price) {
        this.size = size;
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
