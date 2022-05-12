package src;

public class MortgageCalculation implements IMortgageCalculation {
    /**
     * @param sum сумма кредита
     * @param interestRate процентная ставка
     * @param monthCount количество месяцев
     */
    @Override
    public double calculate(long sum, double interestRate, int monthCount) {
        double pow = Math.pow((1 + interestRate),monthCount);
        return sum * ((interestRate * pow) / (pow - 1));
    }
}
