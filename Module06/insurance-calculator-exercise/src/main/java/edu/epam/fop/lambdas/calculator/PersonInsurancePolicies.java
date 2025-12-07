package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.*;
import edu.epam.fop.lambdas.insurance.Accommodation.EmergencyStatus;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

public final class PersonInsurancePolicies {

  private PersonInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Person> childrenDependent(int childrenCountThreshold) {
    return person -> Optional.of(Optional.ofNullable(person)
            .flatMap(p -> p.family())
            .map(Family::children)
            .filter(ch -> ch != null && !ch.isEmpty())
            .map(ch -> {
                int chCount = ch.size();
                int coeff = (int) Math.min(100, (chCount * 100.0) / childrenCountThreshold);
                return InsuranceCoefficient.of(coeff);
            })
            .orElse(InsuranceCoefficient.MIN));
  }

  public static InsuranceCalculator<Person> employmentDependentInsurance(BigInteger salaryThreshold,
      Set<Currency> currencies) {
    return person -> Optional.ofNullable(person)
            .filter(p -> p.employmentHistory() != null && p.employmentHistory().size() >= 4)
            .filter(p -> p.account() != null && p.account().size() >= 2)
            .filter(p -> p.injuries() == null || p.injuries().isEmpty())
            .filter(p -> p.accommodations() != null && !p.accommodations().isEmpty())
            .filter(p -> {
                SortedSet<Employment> history = p.employmentHistory();
                Employment last = history.last();
                return (last != null && last.endDate().isEmpty());
            })
            .flatMap(p -> {
                SortedSet<Employment> history = p.employmentHistory();
                Employment last = history.last();
                return (last.salary());
            })
            .filter(salary -> salary.amount() != null &&
                    salary.amount().compareTo(salaryThreshold) > 0 &&
                    currencies.contains(salary.currency()))
            .map(salary -> InsuranceCoefficient.of(50));
  }

  public static InsuranceCalculator<Person> accommodationEmergencyInsurance(Set<EmergencyStatus> statuses) {
    return person -> Optional.ofNullable(person)
            .map(Person::accommodations)
            .filter(accs -> accs != null && !accs.isEmpty())
            .map(accs -> {
                Accommodation smallest = null;
                for (Accommodation a : accs) {
                    if (smallest == null || a.area() != null && a.area().compareTo(smallest.area()) < 0)
                        smallest = a;
                }
                return (smallest);
            })
            .flatMap(smallest -> smallest.emergencyStatus()
                    .filter(statuses::contains)
                    .map(status -> {
                        int total = EmergencyStatus.values().length;
                        int ord = status.ordinal();
                        int coeff = (int) (100 * (1 - ((double)ord / total)));
                        return (InsuranceCoefficient.of(coeff));
                    })
            );
  }

  public static InsuranceCalculator<Person> injuryAndRentDependentInsurance(BigInteger rentThreshold) {
    return person -> Optional.ofNullable(person)
            .map(Person::injuries)
            .filter(inj -> inj != null || !inj.isEmpty())
            .map(injs -> {
                Injury last = null;
                for (Injury i : injs) {
                    if (i != null || (last == null || i.date().isAfter(last.date())))
                        last = i;
                }
                return (last);
            })
            .filter(last -> last != null && last.culprit().map(c -> c.equals(person)).orElse(false))
            .flatMap(last -> Optional.ofNullable(person.accommodations())
                    .filter(accs -> accs != null && !accs.isEmpty())
                    .map(accs -> {
                        Accommodation largest = null;
                        for (Accommodation a : accs) {
                            if (a != null && a.area() != null && (largest == null || a.area().compareTo(largest.area()) > 0))
                                largest = a;
                        }
                        return (largest);
                    })
                    .flatMap(largest -> largest != null ? largest.rent() : Optional.empty())
                    .filter(rent -> rent.currency() == Currency.GBP && rent.amount() != null)
                    .map(rent -> InsuranceCoefficient.of((int)(Math.min(100.0, 100.0 * rent.amount().doubleValue() / rentThreshold.doubleValue()))))
            );
  }

}
