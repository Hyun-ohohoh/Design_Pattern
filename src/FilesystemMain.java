import java.io.File;

public class FilesystemMain {

    public static void main(String[] args) {

        // 현재 디렉터리 기준
        File currentFile = new File(".");

        // 현재 디렉터리 이름과 depth=0으로 current Directory 객체 생성
        FilesystemComponent current = new Directory(currentFile.getName(), 0);

        // 파일 시스템 재귀 탐색
        traverse(currentFile, (Directory) current);

        // 디렉터리 구조 출력
        System.out.println("\n======= 디렉터리 구조 =======");
        current.display();

        // Serialize 문자열 출력
        System.out.println("\n======= Serialize =======");
        String snapshot = current.serialize();
        System.out.println(snapshot);

        // Serialize 결과를 기반으로 복원된 디렉터리 출력
        System.out.println("\n======= Deserialize =======");
        Directory restored = new Directory(); // 이름, depth는 deserialize에서 덮어씀
        restored.deserialize(snapshot);
        restored.display();
    }

    // 현재 디렉터리를 재귀적으로 순회, MyFile, Directory 객체 생성
    public static void traverse(File file, Directory currentDir) {
        File[] list = file.listFiles();

        if (list == null) {
            return;
        }
            for (File f : list) {
                if (f.isFile()) {
                    FilesystemComponent fileComponent = new MyFile(f.getName(), f.length(), currentDir.depth + 1);
                    currentDir.add(fileComponent);
                } else if (f.isDirectory()) {
                    Directory dir = new Directory(f.getName(), currentDir.depth + 1);
                    currentDir.add(dir);
                    traverse(f, dir);
                }
            }
    }
}



