package pl.javastart.couponcalc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {
    private PriceCalculator priceCalculator;
    private List<Product> products;
    private List<Coupon> coupons;

    @BeforeEach
    public void init() {
        priceCalculator = new PriceCalculator();
        products = new ArrayList<>();
        coupons = new ArrayList<>();
    }

    @Test
    public void shouldReturnZeroForNoProducts() {
        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        products.add(new Product("Masło", 5.99, Category.FOOD));

        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForTwoProductsAndTwoCouponsDifferentCategory() {

        // given
        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));

        coupons.add(new Coupon(Category.FOOD, 50));
        coupons.add(new Coupon(Category.CAR, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(96);
    }

    @Test
    public void shouldReturnPriceForTwoProductsAndTwoCouponsSameCategory() {

        // given
        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));

        coupons.add(new Coupon(Category.FOOD, 50));
        coupons.add(new Coupon(Category.FOOD, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(103);
    }

    @Test
    public void shouldReturnPriceForTwoProductsAndTwoDifferentCoupons() {

        // given
        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Mleko", 4, Category.FOOD));

        coupons.add(new Coupon(Category.FOOD, 50));
        coupons.add(new Coupon(null, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void shouldReturnPriceForFourProductsAndThreeDifferentCoupons() {

        // given
        products.add(new Product("Masło", 5, Category.FOOD));
        products.add(new Product("Mleko", 5, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));
        products.add(new Product("Lampa", 150, Category.HOME));

        coupons.add(new Coupon(Category.FOOD, 50)); //total 255
        coupons.add(new Coupon(null, 10)); //total 234
        coupons.add(new Coupon(Category.HOME, 50)); //total 185

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(185);
    }
}