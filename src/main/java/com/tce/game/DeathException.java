package com.tce.game;

public class DeathException extends Exception {
	private static final long serialVersionUID = 1L;//序列化 为啥忘了
	private String message;

    public DeathException(String message) {
        super(message);
        this.message = message;
    }

    public String getLocalisedMessage() {
        return message;
    }
}
