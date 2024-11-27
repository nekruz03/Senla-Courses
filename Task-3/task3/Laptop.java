import interfaces.IProduct;
import interfaces.IProductPart;

public class Laptop implements IProduct {

    @Override
    public void installFirstPart(IProductPart part) {
        System.out.println("Усановлен корпус, 1 часть");
    }
    @Override
    public void installSecondPart(IProductPart part) {
        System.out.println("Усановлен материнская плата, 2 часть");
    }
    @Override
    public void installThirdPart(IProductPart part) {
        System.out.println("Установлен монитор, 3 часть");
    }
}
