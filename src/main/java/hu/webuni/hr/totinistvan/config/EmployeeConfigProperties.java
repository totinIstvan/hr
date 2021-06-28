package hu.webuni.hr.totinistvan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "employee")
@Component
public class EmployeeConfigProperties {

    private YearsWorked yearsWorked = new YearsWorked();
    private PayrisePercent payrisePercent = new PayrisePercent();

    public YearsWorked getYearsWorked() {
        return yearsWorked;
    }

    public void setYearsWorked(YearsWorked yearsWorked) {
        this.yearsWorked = yearsWorked;
    }

    public PayrisePercent getPayrisePercent() {
        return payrisePercent;
    }

    public void setPayrisePercent(PayrisePercent payrisePercent) {
        this.payrisePercent = payrisePercent;
    }

    public static class YearsWorked {
        private double ten;
        private double five;
        private double twoAndHalf;

        public double getTen() {
            return ten;
        }

        public void setTen(double ten) {
            this.ten = ten;
        }

        public double getFive() {
            return five;
        }

        public void setFive(double five) {
            this.five = five;
        }

        public double getTwoAndHalf() {
            return twoAndHalf;
        }

        public void setTwoAndHalf(double twoAndHalf) {
            this.twoAndHalf = twoAndHalf;
        }
    }

    public static class PayrisePercent {
        private Special special = new Special();
        private Default def = new Default();

        public Default getDef() {
            return def;
        }

        public void setDef(Default def) {
            this.def = def;
        }

        public Special getSpecial() {
            return special;
        }

        public void setSpecial(Special special) {
            this.special = special;
        }
    }

    public static class Special {
        private int ten;
        private int five;
        private int two;
        private int zero;

        public int getTen() {
            return ten;
        }

        public void setTen(int ten) {
            this.ten = ten;
        }

        public int getFive() {
            return five;
        }

        public void setFive(int five) {
            this.five = five;
        }

        public int getTwo() {
            return two;
        }

        public void setTwo(int two) {
            this.two = two;
        }

        public int getZero() {
            return zero;
        }

        public void setZero(int zero) {
            this.zero = zero;
        }
    }

    public static class Default {
        private int five;

        public int getFive() {
            return five;
        }

        public void setFive(int five) {
            this.five = five;
        }
    }
}
