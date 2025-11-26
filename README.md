# 📂 File System Implementation with Design Patterns

> **[명지대학교 개별 과제]** 구조(Structural) 패턴인 **Composite**과 행동(Behavioral) 패턴인 **Memento**를 활용하여, 실제 파일 시스템을 객체지향적으로 모델링하고 상태를 직렬화/역직렬화하는 프로젝트입니다.

## 🎯 Project Goal
* **Composite Pattern**: 파일(Leaf)과 디렉터리(Composite)를 동일한 인터페이스(`FilesystemComponent`)로 추상화하여, 복잡한 트리 구조를 일관성 있게 제어합니다.
* **Memento Pattern**: 객체의 캡슐화를 위배하지 않으면서 현재 트리 구조의 상태를 문자열로 저장(Serialize)하고, 이를 다시 객체로 복원(Deserialize)하는 메커니즘을 구현합니다.
* **Recursive Traversal**: 실제 로컬 디스크의 디렉터리를 DFS(깊이 우선 탐색) 방식으로 순회하며 동적으로 객체 트리를 생성합니다.

## 🛠️ Tech Stack
![Java 21](https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white) ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)
* **Constraints**: 외부 프레임워크(Spring 등) 사용 금지, Java Standard Library(`java.io` 등)만 사용.

## 🏗️ Design & Implementation

### 1. Composite Pattern (Task #1)
파일 시스템의 부분-전체 계층 구조(Part-Whole Hierarchy)를 표현하기 위해 적용했습니다.

* **`FilesystemComponent` (Component)**
  * 파일과 디렉터리의 공통 부모 클래스입니다.
  * `display()`: 자신의 이름과 크기(디렉터리의 경우 하위 항목의 합계)를 출력합니다.
* **`File` (Leaf)**
  * 개별 파일을 나타내며, 더 이상 하위 요소를 가질 수 없습니다.
* **`Directory` (Composite)**
  * 내부 리스트(`List<FilesystemComponent>`)를 통해 자식 요소들을 관리합니다.
  * `add()`: 하위 파일이나 디렉터리를 트리에 추가합니다.
  * `display()` 호출 시 재귀적으로 자식들의 정보를 집계하여 출력합니다.

### 2. Memento Pattern & Serialization (Task #2)
객체의 상태 저장을 위해 직렬화(Serialization) 로직을 직접 구현했습니다.

* **Serialize (직렬화)**
  * 객체 트리를 순회하며 복원 가능한 형태의 Opaque String(문자열)으로 변환합니다.
  * 디렉터리의 구조적 깊이와 포함된 파일 정보를 재귀적으로 문자열에 기록합니다.
* **Deserialize (역직렬화)**
  * 직렬화된 문자열을 파싱(Parsing)하여, 원본과 동일한 구조의 `Directory` 및 `File` 객체 트리를 메모리에 재생성합니다.

## 💻 Execution Flow

1. **Initialization**: 프로그램 실행 시, 현재 디렉터리(`.`)를 기준으로 실제 파일 시스템을 스캔합니다.
2. **Build Tree**: `java.io.File`을 사용하여 파일이면 `File` 객체, 폴더면 `Directory` 객체를 생성하고 트리를 구성합니다(DFS).
3. **Display (Original)**: 생성된 트리의 `display()`를 호출하여 계층 구조와 용량을 들여쓰기(Indentation) 형식으로 출력합니다.
4. **Serialize**: 루트 디렉터리 객체의 `serialize()`를 호출하여 전체 구조를 문자열로 저장합니다.
5. **Deserialize**: 새로운 `Directory` 객체를 생성하고, 위 문자열을 이용해 `deserialize()`를 수행하여 객체 상태를 복제합니다.
6. **Verify**: 복제된 객체의 `display()`를 호출하여 원본과 동일한지 검증합니다.

## 📂 Directory Structure
```bash
src
├── component
│   ├── FilesystemComponent.java  # Common Interface (Abstract Class)
│   ├── File.java                 # Leaf Implementation
│   └── Directory.java            # Composite Implementation
└── Main.java                     # Application Entry Point & Testing
