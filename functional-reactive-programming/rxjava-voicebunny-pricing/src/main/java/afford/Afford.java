package afford;

import rx.Observable;


public class Afford {

    public static void main(String[] args) {
        APIService api = new APIService("30477", "89a5c4688d35ad50dbb5593e1367b363");

        Observable<Balance> oBalance = api.balance();

        Observable
                .from(new Integer[]{1, 20, 40, 60, 80, 100, 120, 140, 150})
                .flatMap(w -> api.quote(w))
                .withLatestFrom(oBalance, (quote, balance) -> new Project(quote.getWords(), quote.getListPrice(), balance.getAmount()))
                .takeWhile(Project::isAffordable)
                .subscribe(
                        project -> System.out.println(project),
                        e -> e.printStackTrace(),
                        () -> System.out.println("Complete")
                );
    }
}
