package com.mercury.platform.shared.hotkey;

import com.mercury.platform.shared.config.Configuration;
import com.mercury.platform.shared.config.descriptor.NotificationSettingsDescriptor;
import com.mercury.platform.shared.store.MercuryStoreCore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorListener;

public class ClipboardListener {
    private final static Logger logger = LogManager.getLogger(ClipboardListener.class);
    public static boolean enabled;
    private static ClipboardListener instance = null;
    private static String lastData;
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    FlavorListener flavorListener;

    private boolean secondRun = false;

    private ClipboardListener() {
        NotificationSettingsDescriptor config = Configuration.get().notificationConfiguration().get();
        enabled = config.isWhisperHelperEnable();

        new Timer(200, e -> {
            if (enabled) {
                try {
                    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                    DataFlavor df = DataFlavor.stringFlavor;
                    if (c.isDataFlavorAvailable(df)) {
                        String message = c.getData(df).toString();
                        if (!message.equals(lastData)) {
                            lastData = message;
                            if (message.toLowerCase().contains("@") &&
                                    (message.toLowerCase().contains("hi, i would like") ||
                                            message.toLowerCase().contains("hi, i'd like") ||
                                            message.toLowerCase().contains("i'd like") ||
                                            (message.toLowerCase().contains("wtb") && message.toLowerCase().contains("(stash")))) {

                                MercuryStoreCore.chatClipboardSubject.onNext(true);
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }).start();

    }

    public static void createListener() {
        if (instance == null) {
            instance = new ClipboardListener();
        }
    }

}
