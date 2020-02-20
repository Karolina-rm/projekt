public enum SHIP {
    BLUE("playerShip3_blue.png", "playerLife3_blue.png"),
    GREEN("playerShip3_green.png", "playerLife3_green.png"),
    ORANGE("playerShip3_orange.png", "playerLife3_orange.png"),
    RED("playerShip3_red.png", "playerLife3_red.png");

    private String urlShip;

    private String urlLife;

    private SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public String getUrlLife() {
        return this.urlLife;
    }


}
