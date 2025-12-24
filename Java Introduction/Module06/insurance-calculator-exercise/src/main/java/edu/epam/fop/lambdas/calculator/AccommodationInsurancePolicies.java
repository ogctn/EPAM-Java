package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import java.math.BigInteger;
import java.util.Optional;

import edu.epam.fop.lambdas.insurance.RepeatablePayment;
import edu.epam.fop.lambdas.insurance.Currency;


public final class AccommodationInsurancePolicies {

  private AccommodationInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  static InsuranceCalculator<Accommodation> rentDependentInsurance(BigInteger divider) {
    return acc -> Optional.ofNullable(acc)
            .flatMap(Accommodation::rent)
            .filter(r -> r.unit() != null && r.unit().getYears() == 0 && r.unit().getMonths() == 1)
            .filter(r -> r.currency() == Currency.USD)
            .filter(r -> r.amount() != null && r.amount().compareTo(BigInteger.ZERO) > 0)
            .map(r -> {
                BigInteger percent = r.amount().multiply(BigInteger.valueOf(100)).divide(divider);
                int val = percent.intValue();
                if (val > 100)
                    return (InsuranceCoefficient.MAX);
                return (InsuranceCoefficient.of(val));
            });
  }

  static InsuranceCalculator<Accommodation> priceAndRoomsAndAreaDependentInsurance(BigInteger priceThreshold,
      int roomsThreshold, BigInteger areaThreshold) {
    return acc -> Optional.of(
            Optional.ofNullable(acc)
            .map(a -> {
                boolean boolPrice = a.price() != null && a.price().compareTo(priceThreshold) >= 0;
                boolean boolRooms = a.rooms() != null && a.rooms().compareTo(roomsThreshold) >= 0;
                boolean boolArea = a.area() != null && a.area().compareTo(areaThreshold) >= 0;
                return (boolPrice && boolRooms && boolArea) ? InsuranceCoefficient.MAX : InsuranceCoefficient.MIN;
            })
            .orElse(InsuranceCoefficient.MIN)
    );
  }
}
