package com.mercury.platform.shared.config.descriptor;

import lombok.Data;

import java.io.Serializable;

@Data
public class VulkanDescriptor implements Serializable {
    private boolean vulkanSupportEnabled;
}
