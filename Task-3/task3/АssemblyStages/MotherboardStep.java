package АssemblyStages;

import bodyes.Motherboard;
import interfaces.ILineStep;
import interfaces.IProductPart;

public class MotherboardStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Сборка материнской платы завершена.");
        return new Motherboard();
    }
}