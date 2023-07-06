package singletone;

public class SingletoneService {
    // class 레벨에 하나만 존재하도록 static으로 선언한다.
    // 외부에서 객체 생성을 못하도록 private으로 선언한다.
    private SingletoneService() {};
    private static final SingletoneService instance = new SingletoneService();

    public static SingletoneService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
