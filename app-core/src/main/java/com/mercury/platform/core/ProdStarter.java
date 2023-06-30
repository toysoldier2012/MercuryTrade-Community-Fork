package com.mercury.platform.core;

import com.mercury.platform.core.misc.SoundNotifier;
import com.mercury.platform.core.update.UpdateClientStarter;
import com.mercury.platform.shared.FrameVisibleState;
import com.mercury.platform.shared.HistoryManager;
import com.mercury.platform.shared.UpdateManager;
import com.mercury.platform.shared.VulkanManager;
import com.mercury.platform.shared.config.Configuration;
import com.mercury.platform.shared.config.MercuryConfigManager;
import com.mercury.platform.shared.config.MercuryConfigurationSource;
import com.mercury.platform.shared.config.descriptor.adr.AdrVisibleState;
import com.mercury.platform.shared.hotkey.ClipboardListener;
import com.mercury.platform.shared.hotkey.HotKeysInterceptor;
import com.mercury.platform.shared.store.MercuryStoreCore;
import com.mercury.platform.shared.wh.WhisperHelperHandler;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProdStarter {
    private static final Logger logger = LogManager.getLogger(ProdStarter.class.getSimpleName());
    public static FrameVisibleState APP_STATUS = FrameVisibleState.HIDE;
    private volatile int delay = 100;

    public void startApplication(boolean alwaysVisible) {
        MercuryConfigManager configuration = new MercuryConfigManager(new MercuryConfigurationSource());
        configuration.load();
        Configuration.set(configuration);
        new SoundNotifier();
        new ChatHelper();
        new HotKeysInterceptor();
        new WhisperHelperHandler();
        ClipboardListener.createListener();

        HistoryManager.INSTANCE.load();

        if (SystemUtils.IS_OS_WINDOWS && !alwaysVisible) {
            MercuryStoreCore.uiLoadedSubject.subscribe((Boolean state) -> {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        char[] className = new char[512];
                        char[] title = new char[512];
                        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();

                        User32.INSTANCE.GetClassName(hwnd, className, 512);
                        User32.INSTANCE.GetWindowText(hwnd, title, 512);

                        if (Native.toString(title).equals("MercuryTrade ADR")) {
                            MercuryStoreCore.adrVisibleSubject.onNext(AdrVisibleState.SHOW);
                        } else {
                            MercuryStoreCore.adrVisibleSubject.onNext(AdrVisibleState.HIDE);
                        }

                        if (!Native.toString(className).equals("POEWindowClass")) {
                            if (APP_STATUS == FrameVisibleState.SHOW) {
                                APP_STATUS = FrameVisibleState.HIDE;
                                MercuryStoreCore.frameVisibleSubject.onNext(FrameVisibleState.HIDE);
                            }
                        } else {
                            if (APP_STATUS == FrameVisibleState.HIDE) {
                                try {
                                    Thread.sleep(delay);
                                    delay = 100;
                                } catch (InterruptedException e) {
                                    logger.error(e);
                                }
                                APP_STATUS = FrameVisibleState.SHOW;
                                MercuryStoreCore.frameVisibleSubject.onNext(FrameVisibleState.SHOW);
                            }
                        }
                    }
                }, 0, 150);
            });
        } else {
            MercuryStoreCore.uiLoadedSubject.subscribe((Boolean state) -> {
                APP_STATUS = FrameVisibleState.SHOW;
                MercuryStoreCore.frameVisibleSubject.onNext(FrameVisibleState.SHOW);
            });
        }
        MercuryStoreCore.showingDelaySubject.subscribe(state -> this.delay = 300);
        MercuryStoreCore.shutdownAppSubject.subscribe(state -> System.exit(0));
    }
}
