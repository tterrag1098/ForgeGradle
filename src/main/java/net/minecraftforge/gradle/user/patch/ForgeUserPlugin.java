package net.minecraftforge.gradle.user.patch;

import static net.minecraftforge.gradle.user.patch.UserPatchConstants.*;
import net.minecraftforge.gradle.common.Constants;
import net.minecraftforge.gradle.tasks.ProcessJarTask;
import net.minecraftforge.gradle.tasks.ProcessSrcJarTask;

public class ForgeUserPlugin extends UserPatchBasePlugin
{
    @Override
    public String getApiName()
    {
        return "forge";
    }

    @Override
    protected String getApiGroup()
    {
        return "net.minecraftforge";
    }

    @Override
    protected void configureDeobfuscation(ProcessJarTask task)
    {
        task.addTransformerClean(delayedFile(FML_AT));
        task.addTransformerClean(delayedFile(FORGE_AT));
    }

    @Override
    protected void configurePatching(ProcessSrcJarTask patch)
    {
        patch.addStage("fml", delayedFile(FML_PATCHES_ZIP), delayedFile(SRC_DIR), delayedFile(RES_DIR));
        patch.addStage("forge", delayedFile(FORGE_PATCHES_ZIP));
    }

    @Override
    protected void doVersionChecks(int buildNumber)
    {
        if (buildNumber == 1517 || buildNumber == 0)
            return; // isolated newer 1.7 forge buildnumber, or 0 for testing

        if (buildNumber < 1048 || buildNumber > 1502)
            throw new IllegalArgumentException("ForgeGradle 1.2 only works for Forge versions 10.12.0.1048 -- 11.14.3.1502. Found: " + buildNumber);
    }

    @Override
    protected String getVersionsJsonUrl()
    {
        // TODO Auto-generated method stub
        return Constants.FORGE_MAVEN + "/net/minecraftforge/forge/json";
    }
}
