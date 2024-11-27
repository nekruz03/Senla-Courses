import bodyes.LaptopMonitorStep;
import interfaces.IAssemblyLine;
import interfaces.ILineStep;
import interfaces.IProduct;

public class App {
    public static void main(String[] args) {
        ILineStep step1 = new LaptopBodyStep();
        ILineStep step2 = new MotherboardStep();
        ILineStep step3 = new LaptopMonitorStep();
        IAssemblyLine assemblyLine = new LaptopAssemblyLine(step1, step2, step3);
        IProduct laptop = new Laptop();
        assemblyLine.assembleProduct(laptop);
    }
}
