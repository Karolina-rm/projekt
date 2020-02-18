public enum SHIP {
    BLUE("playerShip3_blue.png"),
    GREEN("playerShip3_green.png"),
    ORANGE("playerShip3_orange.png"),
    RED("playerShip3_red.png");

    private String urlShip;

    private SHIP(String urlShip) {
        this.urlShip = urlShip;
    }

    public String getUrl() {
        return this.urlShip;
    }


}
