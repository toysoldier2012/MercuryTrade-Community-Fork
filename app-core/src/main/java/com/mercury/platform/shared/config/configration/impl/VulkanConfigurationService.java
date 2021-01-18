package com.mercury.platform.shared.config.configration.impl;

import com.mercury.platform.core.misc.WhisperNotifierStatus;
import com.mercury.platform.shared.config.configration.BaseConfigurationService;
import com.mercury.platform.shared.config.configration.PlainConfigurationService;
import com.mercury.platform.shared.config.descriptor.ApplicationDescriptor;
import com.mercury.platform.shared.config.descriptor.ProfileDescriptor;
import com.mercury.platform.shared.config.descriptor.VulkanDescriptor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;


public class VulkanConfigurationService extends BaseConfigurationService<VulkanDescriptor> implements PlainConfigurationService<VulkanDescriptor> {
    public VulkanConfigurationService(ProfileDescriptor selectedProfile) {
        super(selectedProfile);
    }

    @Override
    public void validate() {
        if (this.selectedProfile.getVulkanDescriptor() == null) {
            this.selectedProfile.setVulkanDescriptor(this.getDefault());
        }
        ApplicationDescriptor applicationDescriptor = this.selectedProfile.getApplicationDescriptor();
        if (!StringUtils.substringAfterLast(applicationDescriptor.getGamePath(), "\\").equals("")) {
            applicationDescriptor.setGamePath(applicationDescriptor.getGamePath() + File.separatorChar);
        }
    }

    @Override
    public VulkanDescriptor get() {
        return this.selectedProfile.getVulkanDescriptor();
    }

    @Override
    public void set(VulkanDescriptor descriptor) {
        this.selectedProfile.setVulkanDescriptor(descriptor);
    }

    @Override
    public VulkanDescriptor getDefault() {
        VulkanDescriptor descriptor = new VulkanDescriptor();
        descriptor.setVulkanSupportEnabled(false);
        return descriptor;
    }

    @Override
    public void toDefault() {
        this.selectedProfile.setVulkanDescriptor(this.getDefault());
    }
}
