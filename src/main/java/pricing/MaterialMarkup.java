package pricing;

public enum MaterialMarkup {

    FOOD(new Markup(13)),
    PHARMACEUTICALS(new Markup(7.5)),
    ELECTRONICS(new Markup(2)),
    OTHER(new Markup(0));

    private Markup markup;

    MaterialMarkup(Markup markup) {
        this.markup = markup;
    }

    public Markup markup() {
        return markup;
    }
}
