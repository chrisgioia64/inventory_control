package problem.multistage_device;

import lombok.Getter;

@Getter
public class MultistageControl {
    public double nll;
    public int quantity;

    public MultistageControl(double nll, int quantity) {
        this.nll = nll;
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return nll + " : " + quantity;
    }
}