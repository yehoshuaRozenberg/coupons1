package Interfaces;

import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon);

    void updateCoupon(Coupon coupon);

    void deleteCoupon(int id);

    ArrayList<Coupon> getAllCoupons();

    Coupon getOneCoupon(int id);

    void addCouponPurchase(int customerId, int couponId);

    void deleteCouponPurchase(int customerId, int couponId);

}
