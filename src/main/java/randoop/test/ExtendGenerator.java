package randoop.test;

import randoop.sequence.ExecutableSequence;

/**
 * This {@code TestCheckGenerator} combines two generators, using the invalid
 * and failure checks from the first, and, if none of those, then returning the
 * output of the second.
 */
public class ExtendGenerator implements TestCheckGenerator {

  private TestCheckGenerator firstGenerator;
  private TestCheckGenerator secondGenerator;

  /**
   * Creates a check generator that generates {@code TestChecks} for
   * {@code ExecutableSequence} objects using the two {@code TestCheckGenerator}
   * objects. Returns checks produced by the second, unless the first produces
   * checks that either have error or invalid behavior.
   *
   * @param firstGenerator
   *          the visitor to identify error and invalid behaviors in the
   *          sequence
   * @param secondGenerator
   *          the visitor to identify other checks for the sequence
   */
  public ExtendGenerator(TestCheckGenerator firstGenerator, TestCheckGenerator secondGenerator) {
    this.firstGenerator = firstGenerator;
    this.secondGenerator = secondGenerator;
  }

  /**
   * {@inheritDoc} Returns checks generated by the two
   * {@code TestCheckGenerator} objects.
   *
   * @return checks object produced by the second generator if the first
   *         generator did not generate invalid or error checks for the
   *         sequence, and otherwise returns those invalid or error checks
   */
  @Override
  public TestChecks visit(ExecutableSequence s) {
    TestChecks checks = firstGenerator.visit(s);
    if (checks.hasInvalidBehavior() || checks.hasErrorBehavior()) {
      return checks;
    } else {
      return secondGenerator.visit(s);
    }
  }
}
