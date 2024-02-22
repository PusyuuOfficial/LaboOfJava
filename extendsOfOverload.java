/*
継承のオーバーロードではそもそもオーバーロードとは同じ関数名で引数が違えば同じ名前でも使えるという機能です。
スーパーメソッドの引数にいれOldクラス内のoldCfunに渡すことで引数ありなしが切り替わる。
*/

class Old {
  public void oldCfun(int x) {
    System.out.println("引数ありバージョン");
  }
  public void oldCfun() {
    System.out.println("引数なしバージョン");
  }
}

class New {
  super();//機能しない。。。本当はこれで行くはずちなみに引数にいれOldクラス内のoldCfunに渡すことで引数ありなしが切り替わる。
  public void newCfun() {
    System.out.println("サブクラス");
  }
}

public class extendsOfOverload {
    public static void main(String[] args) {
        New obj = new New();
        obj.newCfun();
    }
}
