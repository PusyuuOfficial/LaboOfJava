//データ型の内容作成（インスタンスそのもの（設計図ともいう））
class Student {
  public int num;//整数を扱えるようにする
  public void show() {//表示処理
    System.out.println(num);
  }
}
/* 上記のコードはあくまでもデータ型であり実体がない下記で実態の作成 */
class Sample {
  public static void main(String[] args) {
    Student Tarou;//"クラス型の"変数宣言
    Tarou = /* Tarouに代入されるのは実態ではなくアドレス */ new Student();//newは実態"インスタンス"を作る演算子
    Tarou.num = 10;
    Tarou.show();//出力
  }

  public  static void test(String[] args) {
    Student Hanako = new Student();
    Student Matuura = new Student();
    Hanako.num = 25;
    Matuura.num = 50;
    System.out.println(Hanako.num);
    System.out.println(Matuura.num);
  }
}