package com.example.sweetgirl.magiccup1.model;

/**
 * 全部汇总给unity
 */

public class TallScene {
    private FirstScene firstScene;
    private SecondScene secondScene;
    private ThirdScene thirdScene;
    private FourScene fourScene;

    public TallScene(){
        this.firstScene = new FirstScene();
        this.secondScene = new SecondScene();
        this.thirdScene = new ThirdScene();
        this.fourScene = new FourScene();
    }

    public TallScene(FirstScene firstScene) {
        this.firstScene = firstScene;
    }

    public FirstScene getFirstScene() {
        return firstScene;
    }

    public void setFirstScene(FirstScene firstScene) {
        this.firstScene = firstScene;
    }

    public SecondScene getSecondScene() {
        return secondScene;
    }

    public void setSecondScene(SecondScene secondScene) {
        this.secondScene = secondScene;
    }

    public ThirdScene getThirdScene() {
        return thirdScene;
    }

    public void setThirdScene(ThirdScene thirdScene) {
        this.thirdScene = thirdScene;
    }

    public FourScene getFourScene() {
        return fourScene;
    }

    public void setFourScene(FourScene fourScene) {
        this.fourScene = fourScene;
    }
}
