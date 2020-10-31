package com.mercury.platform.shared.wh;

import com.mercury.platform.shared.store.MercuryStoreCore;

public class WhisperHelperHandler {
    public WhisperHelperHandler() {
        MercuryStoreCore.hotKeyReleaseSubject.subscribe(hotKeyDescriptor -> {
//            NotificationSettingsDescriptor config = Configuration.get().notificationConfiguration().get();
//            if (config.isWhisperHelperEnable() && config.getWhisperHelperHotKey().equals(hotKeyDescriptor)) {
//                Timer timer = new Timer(500, action -> {
//                    MercuryStoreCore.tradeWhisperSubject.onNext(true);
//                });
//                timer.setRepeats(false);
//                timer.start();
//            }
        });
    }
}
