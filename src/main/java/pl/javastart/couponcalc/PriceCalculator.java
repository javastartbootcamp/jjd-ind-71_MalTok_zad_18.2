package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        // TODO
        if (products == null || products.isEmpty()) {
            return 0;
        }

        double priceTotalSum = calculateTotalPriceIfNoCoupons(products);

        if (coupons != null && !coupons.isEmpty()) {
            for (Coupon coupon : coupons) {
                double sum = calculateTotalPriceForOneCoupon(products, coupon);
                if (sum < priceTotalSum) {
                    priceTotalSum = sum;
                }
            }
        }
        return roundResult(priceTotalSum);
    }

    private double calculateTotalPriceIfNoCoupons(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(0.0, Double::sum);
    }

    private double calculateTotalPriceForOneCoupon(List<Product> products, Coupon coupon) {
        return products.stream()
                    .map(product -> product.priceAfterDiscount(coupon))
                    .reduce(0.0, Double::sum);

    }

    private double roundResult(double price) {
        return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}