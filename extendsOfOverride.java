/*
overrideとは関数の上書きを行う手法です、オーバーライドの条件は関数名、戻り地、引数が同じであることです。
オーバーライドしたとき、手前の関数は消えないとのこと。
*/

class Old {
  public void overFun() {
    System.out.println("古いメソッド");
  }
}

class New extends Old {
  public void overFun() {
    //super.overFun();継承前の関数の呼び出しで継承前の関数が消えていないことがよくわかる
    System.out.println("新しいメソッド");
  }
}

class extendsOfOverride {
  public static void main(String[] args) { 
    New obj = new New();
    obj.overFun();
  }
}
