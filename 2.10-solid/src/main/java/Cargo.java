public enum Cargo {
    DESENVOLVEDOR(new DezOuVintePorcento()),
    DBA(new QuinzeOuVinceECincoPorcento()),
    TESTER(new QuinzeOuVinceECincoPorcento());

    private final RegraDeCalculo regra;

    Cargo(RegraDeCalculo regra) {
        this.regra = regra;
    }

    public RegraDeCalculo getRegra() {
        return regra;
    }
}