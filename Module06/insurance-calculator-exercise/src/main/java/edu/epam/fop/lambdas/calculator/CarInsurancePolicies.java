package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Car;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class CarInsurancePolicies {

  private CarInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Car> ageDependInsurance(LocalDate baseDate) {
    return car -> Optional.ofNullable(car)
            .map(Car::manufactureDate)
            .map(manufDate -> {
                long days = ChronoUnit.DAYS.between(manufDate, baseDate);
                if (days < 366L)
                    return (InsuranceCoefficient.MAX);
                if (days < 366L * 5)
                    return (InsuranceCoefficient.of(70));
                if (days < 366L * 10)
                    return (InsuranceCoefficient.of(30));
                return (InsuranceCoefficient.MIN);
            });
  }

  public static InsuranceCalculator<Car> priceAndOwningOfFreshCarInsurance(LocalDate baseDate,
      BigInteger priceThreshold, Period owningThreshold) {
    return car -> Optional.ofNullable(car)
            .filter(c -> c.soldDate().isEmpty() || c.soldDate() == null)
            .filter(c -> c.price() != null && c.price().compareTo(priceThreshold) >= 0)
            .filter(c -> c.purchaseDate() != null && !c.purchaseDate().plus(owningThreshold).isBefore(baseDate))
            .map(c -> {
                if (c.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(3))) >= 0)
                    return (InsuranceCoefficient.MAX);
                else if (c.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(2))) >= 0)
                    return (InsuranceCoefficient.of(50));
                else
                    return (InsuranceCoefficient.MIN);
            });
  }

}
