//i.o 라이브러리 File 객체와 구분하기 위한 클래스 이름
public class MyFile extends FilesystemComponent{

    private long size;

    public MyFile(String name, long size, int depth) {
        super(name);
        this.size = size;
        this.depth = depth;
    }

    public MyFile() {
        super("");
        this.size = 0;
        this.depth = 0;
    }

    @Override
    public void display() {
        //파일 크기, 이름 출력
        System.out.println("  ".repeat(depth) + name + " (" + size + " B)");

    }

    public long getSize(){
        return size;
    }

    @Override
    public String serialize() {
        //파일과 디렉토리를 구별하기 위한 구분자 F
        return "F|" + depth + "|" + name + "|" + size;
    }

    @Override
    public void deserialize(String line) {
        String[] parts = line.split("\\|");
        this.depth = Integer.parseInt(parts[1]);
        this.name = parts[2];
        this.size = Long.parseLong(parts[3]);
    }


}
