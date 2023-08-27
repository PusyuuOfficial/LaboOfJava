/* データ型とはなにか、扱うデータの種類や大きさをコンピュータに伝えるための言葉
そのためデータ型には実体がありません。名前をつけることが可能で例えばint nam = 1;ここではintという基本型にnamという名前がついている　まとめて変数という（私の感想）
 データ型には二種類あってC言語でもおなじみの基本型int,char,double,float,等があるように参照型があります。配列 、 
 基本型と参照型の違い⇓
 　　　　　　　　　　　基本型　　　　参照型
 格納するデータ→　　　データ　　　　アドレス
 扱えるデータの数→　　一つ　　　　　複数
 データ型の内容→　　　きまっている　自由に作れる

基本型は代入した数値を直接格納され、扱えるデータは一つだけ、指定のメモリーサイズ
 参照型は自分たちで作れるデータ型、アドレスを格納するためのデータ型

 参照型がなぜアドレスを格納するのか：扱うデータ量が大きいから、データの大きさが予測できないから。

 クラスとは変数と関数をまとめて扱う仕組み
 class kenn {
    int kenntyou = (34);
 }
 class mati {
    int matiyakuba = "4";    
}
のようにわかりやすくまとめることができるし部品化できる。

classの使い方
class Student {
  public int num;
  public void show() {
    System.out.println(nam):
  }
}
上記のclassのあとにあるStudentはclassに対する名前でその中にあるpublicというのは外部からクラス内に要素を読み書きできるのかの設定、今回はpublicなので外部からの読み書きが可能。
 */

class Student {
  public int num;
  public void show() {
    System.out.println(num);
  }
}

class Sample {
  public static void main(String[] args) {
    Student Tarou;
    Tarou = new Student(); /* new演算子は実体を作成　Studentクラスをnewインスタンスで実体を作成（）がクラスからインスタンスが作られるタイミング　注意点はtarouに代入されるのは実弟ではなく実体のアドレスであること */
    Tarou.num = 10;
    Tarou.show();/* 実体が作られるタイミングで発動する関数 */
  }
}