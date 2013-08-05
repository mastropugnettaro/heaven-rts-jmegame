/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rts.gameplay.base;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Random;
import rts.core.entity.RTSEntityManager;
import rts.gameplay.RTSPlayer;
import rts.gameplay.ai.CountryAI;
import rts.gameplay.building.FighterBuidling;
import rts.gameplay.units.Fighter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Country {

    String name;
    int population;
    int popLimit;
    int money;
    int food;
    int energy;
    int wood;
    int stones;
    int gems;
    ArrayList<Building> buildings = new ArrayList<Building>();
    ArrayList<Unit> units = new ArrayList<Unit>();
    ArrayList<Skill> learnableSkill;
    ColorRGBA color;
    private RTSPlayer player;
    Random random = new Random();
    CountryAI countryAI;

    public CountryAI getCommander() {
        return this.countryAI;
    }

    enum Countries {

        Human, Alien, Hybrid
    }

    public Country(String name) {
        this.name = "Country" + name;
        population = 1;
        money = 50;
        food = 50;
        energy = 50;
        wood = 50;
        stones = 50;
        gems = 50;
    }

    public RTSPlayer getPlayer() {
        return player;
    }

    public void setPlayer(RTSPlayer player) {
        this.player = player;

        //if (player.isAI) {
        if (countryAI == null) {
            countryAI = new CountryAI(this);
        }
        //}
    }

    public void paidCost(RTSCost cost) {
        this.food -= cost.food;
    }

    public RTSCost getAsCost() {
        RTSCost tempCost = new RTSCost();
        tempCost.food = this.food;
        return tempCost;
    }

    public void createRandomMap(RTSEntityManager entityManager, Vector3f pos, Vector3f limit) {

        System.out.println(" Create random map");
        for (int i = 0; i <= 10; i++) {
            Building building = new FighterBuidling(name + "Building" + i);
            building.setCountry(this);
            building.setGroup(this.name);
            building.setMapPosition(pos.add(randomVec3(limit)));
            buildings.add(building);
        }
        for (int i = 0; i <= 10; i++) {
            Fighter unit = new Fighter(name + "Fighter" + i);
            unit.setCountry(this);
            unit.setGroup(this.name);
            unit.setMapPosition(pos.add(randomVec3(limit)));
            units.add(unit);
        }

    }

    public void update(float tpf) {
    }

    public void think() {
        // involve AI
    }

    Vector3f randomVec3(Vector3f limit) {

        float x = random.nextFloat() * limit.x;
        float y = random.nextFloat() * limit.y;
        float z = random.nextFloat() * limit.z;
        return new Vector3f(x, 0, z);
    }

    Vector3f randomVec3(float min, float max) {
        float limit = min - max;
        float x = min + random.nextFloat() * limit;
        float y = min + random.nextFloat() * limit;
        float z = min + random.nextFloat() * limit;
        return new Vector3f(x, 0, z);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopLimit() {
        return popLimit;
    }

    public void setPopLimit(int popLimit) {
        this.popLimit = popLimit;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public RTSUnitBase findNearestEnemy(RTSUnitBase who) {
        return null;
    }
}
