package edu.epam.fop.lambdas;

import java.util.function.DoubleFunction;

public interface PercentageFormatter {

  DoubleFunction<String> INSTANCE = v -> String.format("%.1f %%", v * 100)
          .replace(".0", "");
}
