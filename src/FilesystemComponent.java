public abstract class FilesystemComponent {

    protected String name;
    protected int depth;

    public FilesystemComponent(String name) {
        this.name = name;
    }

    public abstract void display();

    public abstract String serialize();

    public abstract void deserialize(String data);
}
