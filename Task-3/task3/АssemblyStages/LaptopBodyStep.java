package АssemblyStages;

import bodyes.LaptopBody;
import interfaces.ILineStep;
import interfaces.IProductPart;

public class LaptopBodyStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Сборка корпуса ноутбука завершена.");
        return new LaptopBody();
    }
}