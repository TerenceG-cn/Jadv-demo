package com.tce.game;

import com.tce.game.repository.EncounteredNpcRepository;
import com.tce.game.repository.ItemRepository;
import com.tce.game.repository.LocationRepository;
import com.tce.game.repository.NpcRepository;

/**
 * There is no Dependency Injection framework yet used / chosen, for creating Beans (runtime singletons / prototypes). 
 * So this class will contain those Beans.
 */
public final class GameBeans {// to do

    public static ItemRepository getItemRepository() {
        return ItemRepository.createRepo();
    }

    public static LocationRepository getLocationRepository() {
        return LocationRepository.createRepo("");
    }

    public static LocationRepository getLocationRepository(String profile) {
        return LocationRepository.createRepo(profile);
    }

    public static NpcRepository getNpcRepository() {
        return EncounteredNpcRepository.createRepo();
    }
}
