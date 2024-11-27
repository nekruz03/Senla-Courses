package bodyes;

import interfaces.ILineStep;
import interfaces.IProductPart;
import АssemblyStages.LaptopMonitor;

public class LaptopMonitorStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Сборка монитора ноутбука завершена.");
        return new LaptopMonitor();
    }
}