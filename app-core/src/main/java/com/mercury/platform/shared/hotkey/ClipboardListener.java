package com.mercury.platform.shared.hotkey;

import com.mercury.platform.shared.store.MercuryStoreCore;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class ClipboardListener {
    private static ClipboardListener instance = null;
    private static String lastData;

    private ClipboardListener() {
        new Timer(200, e -> {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            DataFlavor df = DataFlavor.stringFlavor;
            if (c.isDataFlavorAvailable(df)) {
                try {
                    String message = c.getData(df).toString();
                    if (!message.equals(lastData)) {
                        lastData = message;
                        if (message.toLowerCase().contains("@") &&
                            (message.toLowerCase().contains("hi, i would like") ||
                             message.toLowerCase().contains("hi, i'd like") ||
                             message.toLowerCase().contains("i'd like") ||
                             (message.toLowerCase().contains("wtb") && message.toLowerCase().contains("(stash")))) {

                            System.out.println("ClipBoard UPDATED: " + message);
                            MercuryStoreCore.chatClipboardSubject.onNext(true);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        }).start();


//        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> {
//            Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            try {
//                if (systemClipboard.getContents(this) != null && systemClipboard.getContents(this).isDataFlavorSupported(DataFlavor.stringFlavor)) {
//                    Object transferData = systemClipboard.getContents(this).getTransferData(DataFlavor.stringFlavor);
//                    if (transferData instanceof String) {
//                        String message = (String) transferData;
//
//                        if (message.to
//                        LowerCase().contains("@") &&
//                            (message.toLowerCase().contains("hi, i would like") ||
//                             message.toLowerCase().contains("hi, i'd like") ||
//                             message.toLowerCase().contains("i'd like") ||
//                             (message.toLowerCase().contains("wtb") && message.toLowerCase().contains("(stash")))) {
//
//                            System.out.println("ClipBoard UPDATED: " + message);
//                            MercuryStoreCore.chatClipboardSubject.onNext(true);
//                        }
//                    }
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    public static void createListener() {
        if (instance == null) {
            instance = new ClipboardListener();
        }
    }
}
