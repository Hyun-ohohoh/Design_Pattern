import java.util.*;

public class Directory extends FilesystemComponent {

    private List<FilesystemComponent> list = new ArrayList<>();

    public Directory(String name, int depth) {
        super(name);
        this.depth = depth;
    }

    public Directory() {
        super("");
        this.depth = 0;
    }

    public void add(FilesystemComponent component) {
        list.add(component);
    }

    @Override
    public void display() {
        long totalSize = getSize();
        System.out.println("  ".repeat(depth) + name + "/ (total : " + totalSize + " B)");

        for (FilesystemComponent component : list) {
            component.display();
        }
    }

    public long getSize() {
        long sum = 0;
        for (FilesystemComponent component : list) {
            if (component instanceof MyFile f) {
                sum += f.getSize();
            } else if (component instanceof Directory d) {
                sum += d.getSize();
            }
        }
        return sum;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        //파일과 디렉토리를 구별하기 위한 구분자 D
        sb.append("D|").append(depth).append("|").append(name).append("\n");
        for (FilesystemComponent component : list) {
            sb.append(component.serialize()).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public void deserialize(String data) {
        String[] lines = data.split("\n");
        Stack<Directory> stack = new Stack<>();

        for (String line : lines) {
            if (line.startsWith("D|")) {
                // 디렉터리인 경우
                String[] parts = line.split("\\|");
                Directory dir = new Directory();
                dir.depth = Integer.parseInt(parts[1]);
                dir.name = parts[2];

                // 부모 디렉터리 찾기
                while (!stack.isEmpty() && stack.peek().depth >= dir.depth) {
                    stack.pop();
                }

                if (!stack.isEmpty()) {
                    stack.peek().add(dir);
                }

                stack.push(dir);


                if (dir.depth == 0) {
                    this.name = dir.name;
                    this.depth = dir.depth;
                    this.list = dir.list; // 참조 공유
                }

            } else if (line.startsWith("F|")) {
                // 파일인 경우
                MyFile file = new MyFile();
                file.deserialize(line);

                // 부모 디렉터리 찾기
                while (!stack.isEmpty() && stack.peek().depth >= file.depth) {
                    stack.pop();
                }

                if (!stack.isEmpty()) {
                    stack.peek().add(file);
                }
            }
        }
    }
}

