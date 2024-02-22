/*
extendsは増やせるけど消せない、継承元はすでにテスト済みで安心して使えることをもとにしてるから
大規模開発でも複数のクラス継承から問題を探す必要がなくなるから継承元は変えられないという仕組み

classのアクセス指定子の話↓。
もし継承をする際に関数の呼び出し範囲を指定したい場合にpublicまたはprivateだけだときついです
それはなぜかといいますとpublicだと全公開でどこからも見えてしまいすし逆にprivateだと継承先でもアクセスできなくなってしまいます。
そんなときに使えるのがprotectedを使うことで外部からアクセスされる心配もなくなりますし、継承先でも使用ができるという状態にできます。

super()関数は自動的に追加されるが継承元で引数の設定がある場合はスーパーメソッド（関数）の引数に継承元の引数を渡す必要があり、
もし渡さないと継承元関数には引数がないのでエラーになってしまうというわけです。んでsuperメソッド自体が言うてしまえば継承元の関数なので継承先のクラスに関数を作って入れても無意味になることに注意。
*/

class Old {
  public int num;
  public void OldCfun() {
    System.out.println("スーパークラス");
  }
}
class New extends Old {
  //ここにはsuper()関数が自動的に追加され継承される。
  public int num2;
  public void NewCfun() {
    System.out.println("サブクラス");
  }
}

class extendsTamesi {
  public static void main(String[] args) {
    New obj = new New();
    obj.num = 10;
    obj.num2 = 20;
    System.out.println(obj.num + "継承された者");
    System.out.println(obj.num2 + "継承された者があるクラスの者");
  }
}

/*動画で紹介されてたコード
class Old {
  public void Old() {
    System.out.println("スーパークラス");
  }
}
class New extends Old {
  public void New() {
        //ここにはsuper()関数が自動的に追加され継承される。
    System.out.println("サブクラス");
  }
}

class extendsTamesi {
  public static void main(String[] args) {
    New obj = new New();
  }
}
*/
