package rts.gameplay;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import rts.RTSWorldManager;
import rts.core.entity.RTSEntityManager;
import rts.core.entity.RTSSpatialEntity;
import rts.gameplay.base.Building;
import rts.gameplay.base.Country;
import rts.gameplay.base.Unit;
import rts.gameplay.league.RTSGameMatch;
import rts.gameplay.league.RTSPlayerTeam;
import rts.gameplay.manager.BuildingManager;
import rts.gameplay.manager.UnitManager;
import rts.stage.RTSSelectManager;
import sg.atom.core.StageManager;

/**
 *
 * @author hungcuong
 */
public class RTSGamePlay {

    protected RTSGamePlayManager gamePlayManager;
    protected RTSSelectManager selectManager;
    protected RTSEntityManager entityManager;
    protected UnitManager unitManager;
    protected BuildingManager buildingManager;
    // RTS
    ArrayList<RTSPlayer> players;
    ArrayList<RTSPlayerTeam> teams;
    private StageManager stageManager;
    RTSGameMatch currentMatch;

    public RTSGamePlay(RTSGamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    public void init() {
        this.stageManager = gamePlayManager.getStageManager();
        this.selectManager = gamePlayManager.getSelectManager();
        this.entityManager = gamePlayManager.getEntityManager();
        this.unitManager = gamePlayManager.getUnitManager();
        this.buildingManager = gamePlayManager.getBuildingManager();

    }

    public void setupPlayers() {
        teams = new ArrayList<RTSPlayerTeam>();
        players = new ArrayList<RTSPlayer>();

        RTSPlayer player1 = new RTSPlayer(stageManager, "Player1");
        RTSPlayer player2 = new RTSPlayer(stageManager, "Player1");
        gamePlayManager.setMainPlayer(player1);
        teams.add(new RTSPlayerTeam("Hanoi"));
        teams.add(new RTSPlayerTeam("Singapore"));

        players.add(player1);
        players.add(player2);
    }

    public void startGame(RTSGameMatch match) {
        this.currentMatch = match;

        //FIXME: Assign Players to team. Should get from host/match settings
        teams.get(0).getPlayers().add((RTSPlayer) players.get(0));
        teams.get(1).getPlayers().add((RTSPlayer) players.get(1));

        Country c1 = createCountry("Earth", new Vector3f(140, 0, 220), new Vector3f(100, 0, 100));
        c1.setColor(ColorRGBA.Blue);
        ((RTSPlayer) gamePlayManager.getMainPlayer()).setCountry(c1);

        Country c2 = createCountry("Alien", new Vector3f(300, 0, 230), new Vector3f(100, 0, 100));
        c2.setColor(ColorRGBA.Red);
        ((RTSPlayer) players.get(1)).isAI = true;
        ((RTSPlayer) players.get(1)).setCountry(c2);
    }

    public Country createCountry(String countryName, Vector3f pos, Vector3f limit) {
        Country country = new Country(countryName);

        country.createRandomMap(entityManager, pos, limit);
        for (Unit unit : country.getUnits()) {
            entityManager.addEntity(unit);
        }

        for (Building building : country.getBuildings()) {
            entityManager.addEntity(building);
        }
        return country;
    }

    public void selectUnit() {
        selectManager.setCurrentFunction("SelectMulti");
        String actionName = "";
        // what is the current function
        if (actionName.equals("mouseLeft")) {
            selectManager.setCurrentFunction("SelectUnit");
        } else if (actionName.equals("mouseRight")) {
            selectManager.setCurrentFunction("None");
        }
        //worldManager.setShow3DCussor(true);

        //currentFunction = "MovePos";
    }

    public void moveUnit() {

        //doSelectPos();
        //worldManager.setShow3DCussor(true);
        ArrayList<RTSSpatialEntity> selection = selectManager.getCurrentSelection(RTSSpatialEntity.class);
        if (!selection.isEmpty()) {
            for (RTSSpatialEntity selectedEntity : selection) {
                Vector3f cursor3dPos = getWorldManager().getCursor3dPos();
                if (cursor3dPos != null) {
                    //System.out.println(" " + selectedEntity.type + " " + selectedEntity.getClass().toString());
                    if (Unit.class.isAssignableFrom(selectedEntity.getClass())) {
                        System.out.println("Move " + selectedEntity.name + "      from : " + selectedEntity.getMapPosition() + "    to : " + cursor3dPos);
                        unitManager.moveUnitTo(selectedEntity, cursor3dPos);

                    }
                    //Logger.getLogger(RTSGamePlayManager.class.getName()).info("Move " + selectedEntity.name + " to " + worldManager.get3dCursorPos());

                }
            }
        }

    }

    public void attackUnit() {
    }

    public void queueTroop() {
    }

    public void createBuilding() {
    }

    public void controlUnitAction(String actionName) {
        ArrayList<RTSSpatialEntity> selection = selectManager.getCurrentSelection(RTSSpatialEntity.class);
        if (!selection.isEmpty()) {
            RTSSpatialEntity selectedEntity = selection.get(0);
            //System.out.println(" " + selectedEntity.type + " " + selectedEntity.getClass().toString());
            if (Unit.class.isAssignableFrom(selectedEntity.getClass())) {
                unitManager.doAction(selectedEntity, actionName);

            }
        }
    }

    public RTSWorldManager getWorldManager() {
        return gamePlayManager.getWorldManager();
    }

    public ArrayList<RTSPlayer> getPlayers() {
        return players;
    }

    public ArrayList<RTSPlayerTeam> getTeams() {
        return teams;
    }
}
