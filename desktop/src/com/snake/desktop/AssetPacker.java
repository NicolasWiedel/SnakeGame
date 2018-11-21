package com.snake.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay");

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/ui",
                ASSETS_PATH + "/ui",
                "ui");
    }
}
