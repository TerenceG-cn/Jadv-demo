package com.tce.game.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tce.game.GameBeans;
import com.tce.game.QueueProvider;
import com.tce.game.items.Item;
import com.tce.game.items.Storage;
import com.tce.game.navigation.ILocation;
import com.tce.game.repository.ItemRepository;
import com.tce.game.repository.LocationRepository;

public class Player {

	protected static ItemRepository itemRepo = GameBeans.getItemRepository();
	protected static LocationRepository locationRepo = GameBeans.getLocationRepository();
	private ILocation location;
	private int xp;// ？
	private String type;// player类型
	private static HashMap<String, Integer> characterLevels = new HashMap<String, Integer>();// 角色等级
	private String name;
	private int health;
	private int healthMax;
	private int damage;
	private int armour;//护甲
	private int luck;//幸运值
	private String equipment;//装备
	private int level;//等级
	private String stealth;//隐形
	

	// This is known as the singleton pattern. It allows for only 1 instance of a player.
	private static Player player;

	public Player() {

	}

	protected static void setUpCharaterLevels() {// to be better
		characterLevels.put("Sewer Rat", 5);
		characterLevels.put("Recruit", 3);
		characterLevels.put("Syndicate Member", 4);// 辛迪加
		characterLevels.put("Brotherhood Member", 4);// 兄弟会
	}

	public HashMap<String, Integer> getCharacterLevels() {
		return characterLevels;
	}

	public String getType() {// getCurrentCharacterType
		return this.type;
	}

	public void setType(String newCharacterType) {// setCurrentCharacterType
		this.type = newCharacterType;
	}

	/* profile */
	protected static String getProfileFileName(String name) {
		return "json/profiles/" + name + "/" + name + "_profile.json";
	}

	public static boolean profileExists(String name) {
		File file = new File(getProfileFileName(name));
		return file.exists();
	}

	public static Player load(String fileName) {
		Player player = new Player();// ..
		JsonParser parser = new JsonParser();
		String filenameString = getProfileFileName(fileName);
		try {
			Reader reader = new FileReader(fileName);
			JsonObject json = parser.parse(reader).getAsJsonObject();
			player.setName(json.get("name").getAsString());
			player.setHealthMax(json.get("healthMax").getAsInt());
			player.setHealth(json.get("health").getAsInt());
			player.setArmour(json.get("armour").getAsInt());
			player.setDamage(json.get("damage").getAsInt());
			player.setLevel(json.get("level").getAsInt());
			player.setXP(json.get("xp").getAsInt());
			player.setStrength(json.get("strength").getAsInt());
			player.setIntelligence(json.get("intelligence").getAsInt());
			player.setDexterity(json.get("dexterity").getAsInt());
			player.setLuck(json.get("luck").getAsInt());
			player.setStealth(json.get("stealth").getAsInt());
			player.setType(json.get("type").getAsString());
			HashMap<String, Integer> charLevels = new Gson().fromJson(json.get("types"), new TypeToken<HashMap<String, Integer>>(){}.getType());
            player.setCharacterLevels(charLevels);
            if (json.has("equipment")) {
                Map<String, EquipmentLocation> locations = new HashMap<>();
                locations.put("head", EquipmentLocation.HEAD);
                locations.put("chest", EquipmentLocation.CHEST);
                locations.put("leftArm", EquipmentLocation.LEFT_ARM);
                locations.put("leftHand", EquipmentLocation.LEFT_HAND);
                locations.put("rightArm", EquipmentLocation.RIGHT_ARM);
                locations.put("rightHand", EquipmentLocation.RIGHT_HAND);
                locations.put("bothHands", EquipmentLocation.BOTH_HANDS);
                locations.put("bothArms", EquipmentLocation.BOTH_ARMS);
                locations.put("legs", EquipmentLocation.LEGS);
                locations.put("feet", EquipmentLocation.FEET);
                HashMap<String, String> equipment = new Gson().fromJson(json.get("equipment"), new TypeToken<HashMap<String, String>>(){}.getType());
               Map<EquipmentLocation, Item> equipmentMap = new HashMap<>();
               for(Map.Entry<String, String> entry : equipment.entrySet()) {
                   EquipmentLocation el = locations.get(entry.getKey());
                   //Item i = itemRepo.getItem(entry.getValue());
                   //equipmentMap.put(el, i);
               }
               player.setEquipment(equipmentMap);
            }
		}catch (Exception e) {
			// TODO: handle exception
		}
		return player;
	}

	public static Player getInstance(String playerClass){
        player = new Player();
        JsonParser parser = new JsonParser();
        String fileName = "json/original_data/npcs.json";
        try {
            Reader reader = new FileReader(fileName);
            JsonObject npcs = parser.parse(reader).getAsJsonObject().get("npcs").getAsJsonObject();
            JsonObject json = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : npcs.entrySet()) {
                if (entry.getKey().equals(playerClass)) {
                    json = entry.getValue().getAsJsonObject();
                }
            }

            player.setName(json.get("name").getAsString());
            player.setHealthMax(json.get("healthMax").getAsInt());
            player.setHealth(json.get("health").getAsInt());
            player.setArmour(json.get("armour").getAsInt());
            player.setDamage(json.get("damage").getAsInt());
            player.setLevel(json.get("level").getAsInt());
            player.setXP(json.get("xp").getAsInt());
            player.setStrength(json.get("strength").getAsInt());
            player.setIntelligence(json.get("intelligence").getAsInt());
            player.setDexterity(json.get("dexterity").getAsInt());
            setUpVariables(player);
            JsonArray items = json.get("items").getAsJsonArray();
            for (JsonElement item : items) {
                player.addItemToStorage(itemRepo.getItem(item.getAsString()));
            }
            Random rand = new Random();
            int luck = rand.nextInt(3) + 1;
            player.setLuck(luck);
            player.setStealth(json.get("stealth").getAsInt());
            player.setIntro(json.get("intro").getAsString());
            if (player.getName().equals("Recruit")) {
                player.type = "Recruit";
            } else if (player.getName().equals("Sewer Rat")) {
                player.type = "Sewer Rat";
            } else {
                QueueProvider.offer("Not a valid class");
            }
            reader.close();
            setUpCharaterLevels();
        } catch (FileNotFoundException ex) {
            QueueProvider.offer( "Unable to open file '" + fileName + "'.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return player;
    } 
	
    private Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

	private void setIntro(String asString) {
		// TODO Auto-generated method stub
		
	}

	private void addItemToStorage(Object item) {
		// TODO Auto-generated method stub
		
	}

	public static void setUpVariables(Player player) {
        float maxWeight = (float)Math.sqrt(player.getStrength()*300);
        player.setStorage(new Storage(maxWeight));
    }
	
	private void setStorage(Storage storage) {
		// TODO Auto-generated method stub
		
	}

	private int getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void setEquipment(Map<EquipmentLocation, Item> equipmentMap) {
		// TODO Auto-generated method stub
		
	}

	private void setCharacterLevels(HashMap<String, Integer> charLevels) {
		// TODO Auto-generated method stub
		
	}

	private void setStealth(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setLuck(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setDexterity(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setIntelligence(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setStrength(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setXP(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setLevel(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setDamage(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setArmour(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setHealth(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setHealthMax(int asInt) {
		// TODO Auto-generated method stub

	}

	private void setName(String asString) {
		// TODO Auto-generated method stub

	}

}
