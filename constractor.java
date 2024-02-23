/*
コンストラクターとは、インスタンス（設計図クラス）が作られたタイミングで実行される関数で、初期化（初期値の代入）をする関数でもある。
特徴は、戻り地がないこと、もし戻り値をつけるとエラーになる。他にも引数や型クラス名と同じ関数名であること
*/

class New {
  public int number;//変数を設定
  public New() {
    number = 10;//初期化
    System.out.println("コンストラクタの発動の確認");
  }
}

class HikiNew {
  public int num;//整数の変数を型宣言
  public HikiNew(int x) {//仮引数を指定
    num = x;//仮引数が得た数値をnumに代入
    System.out.println("引数には " + num + "が代入されました。");//それらの結果を表示
  }
}

public class constractor {
  public static void main(String[] args) {
    New obj = new New();
    System.out.println("初期化した値は" + obj.number + "です。");//呼び出しと表示
    HikiNew obj2 = new HikiNew(20);//ヒキニューへ値の代入
  }
}
