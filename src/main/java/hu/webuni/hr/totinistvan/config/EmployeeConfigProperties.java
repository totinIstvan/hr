package hu.webuni.hr.totinistvan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "employee")
@Component
public class EmployeeConfigProperties {

    private YearsWorked yearsWorked = new YearsWorked();
    private PayRisePercent payRisePercent = new PayRisePercent();

    public YearsWorked getYearsWorked() {
        return yearsWorked;
    }

    public void setYearsWorked(YearsWorked yearsWorked) {
        this.yearsWorked = yearsWorked;
    }

    public PayRisePercent getPayRisePercent() {
        return payRisePercent;
    }

    public void setPayRisePercent(PayRisePercent payRisePercent) {
        this.payRisePercent = payRisePercent;
    }

    public static class YearsWorked {
        private List<Double> years;

        public List<Double> getYears() {
            return years;
        }

        public void setYears(List<Double> years) {
            this.years = years;
        }
    }

    public static class PayRisePercent {
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
        private List<Integer> percents;

        public List<Integer> getPercents() {
            return percents;
        }

        public void setPercents(List<Integer> percents) {
            this.percents = percents;
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
