package week1.patterns;

/**
 * Abstract Factory Pattern - Creates families of related objects
 */
public class AbstractFactory {
    
    public static void main(String[] args) {
        UIFactory windowsFactory = new WindowsUIFactory();
        UIFactory macFactory = new MacUIFactory();
        
        createUI(windowsFactory);
        createUI(macFactory);
    }
    
    private static void createUI(UIFactory factory) {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        button.render();
        checkbox.render();
    }
}

interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

interface Button {
    void render();
}

interface Checkbox {
    void render();
}

class WindowsUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Windows Button");
    }
}

class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Mac Button");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Windows Checkbox");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Mac Checkbox");
    }
}
