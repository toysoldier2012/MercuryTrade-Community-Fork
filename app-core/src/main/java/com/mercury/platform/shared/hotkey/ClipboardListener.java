package com.mercury.platform.shared.hotkey;

import com.mercury.platform.shared.store.MercuryStoreCore;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class ClipboardListener {
    private static ClipboardListener instance = null;

    private ClipboardListener() {
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> {
            Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                if (systemClipboard.getContents(this) != null && systemClipboard.getContents(this).isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    Object transferData = systemClipboard.getContents(this).getTransferData(DataFlavor.stringFlavor);
                    if (transferData instanceof String) {
                        String message = (String) transferData;

                        if (message.contains("@") &&
                            (message.contains("Hi, I would like") ||
                             message.contains("Hi, I'd like") ||
                             message.contains("I'd like") ||
                             (message.contains("wtb") && message.contains("(stash")))) {

                            System.out.println("ClipBoard UPDATED: " + message);
                            MercuryStoreCore.chatClipboardSubject.onNext(true);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void createListener() {
        if (instance == null) {
            instance = new ClipboardListener();
        }
    }
}
