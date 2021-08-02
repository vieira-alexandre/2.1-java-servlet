package br.com.alura.leilao.desafios;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class MyMatcher extends TypeSafeMatcher<Leilao> {
    private final Lance lance;

    @Factory
    public static Matcher<Leilao> temUmLance(Lance lance) {
        return new MyMatcher(lance);
    }

    public MyMatcher(Lance lance) {
        this.lance = lance;
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("Leilao possui lance: " + lance);
    }

    @Override
    protected boolean matchesSafely(Leilao item) {
        return item.getLances().contains(lance);
    }

}
