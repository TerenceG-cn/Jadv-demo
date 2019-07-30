package com.tce.game;

/**
 * There are two modes in which you can run the game.
 * <ul>
 * <li>stand-alone<p>Single computer, Single player (default mode)</p></li> 
 * <li>client server<p>Single or Multiple computer(s), Multiple players possible</p></li>
 * </ul>
 */
public enum GameModeType {
    /** 独立架构 */
    STAND_ALONE,
    /** 客户端部分 */
    CLIENT,
    /** 服务器部分 */
    SERVER
}
