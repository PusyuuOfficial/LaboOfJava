/*
継承のオーバーロードではそもそもオーバーロードとは同じ関数名で引数が違えば同じ名前でも使えるという機能です。
スーパーメソッドの引数にいれOldクラス内のOldに渡すことで引数ありなしが切り替わる。
注意点継承の際は関数名とクラス名を同じにしなければならない。
オーバーライドの際は引数の個数や型は同じでなければなりません。
しかし、引数の有無は異なっても構いません。これを、オーバーロード（overloading）と呼びます。

コンストラクタは戻り地を持たないそうです。
*/
class Old {
  public Old(int x) {
    System.out.println("引数ありバージョン" + x);
  }
  public Old() {
    System.out.println("引数なしバージョン");
  }
}

class New extends Old {
  public New() {
    super();//機能しない。。。本当はこれで行くはずちなみに引数にいれOldクラス内のoldCfunに渡すことで引数ありなしが切り替わる。
    System.out.println("サブクラス");
  }
}

class extendsOfOverload {
  public static void main(String[] args) {
    New obj = new New();
  }
}
