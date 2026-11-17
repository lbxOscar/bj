# Blackjack Tracker — Fabric prototype

Client-side prototype for Minecraft Java 1.21.11 / Fabric.

## Included in v0.1
- Fabric client mod scaffold
- Dark-purple HUD at the top-left
- Right Shift toggles the HUD
- Scans the hotbar for filled maps and persists observed map IDs
- Basic player/dealer score model (not yet connected to card recognition)

## Build
Requires JDK 21 and Gradle.

```bash
gradle build
```

The mod jar is produced in `build/libs/`.

## Install
Copy the built jar into `.minecraft/mods` alongside Fabric Loader and Fabric API for Minecraft 1.21.11.

## Current limitations
- Map IDs are saved, but the mod does not yet identify the artwork/card value.
- Scores are not inferred from dispenser contents yet.
- No automatic card extraction, insertion, or outcome manipulation is implemented.
- The prototype does not interact with server gambling logic.

## Next development stage
Add a safe card-observation layer: inspect map items as they enter the player's inventory, let the user assign a card identity/value to each known map ID, then update the score HUD from observed card events.


## Build on GitHub (no local Gradle setup)
1. Create a new GitHub repository.
2. Upload the *contents* of this project folder, including the hidden `.github` folder.
3. Open the repository's **Actions** tab and enable Actions if prompted.
4. The workflow runs on each push. You can also select **Build Fabric mod** and click **Run workflow**.
5. When the run finishes, open it and download the artifact named `blackjack-tracker-fabric-1.21.11`.
6. Extract the downloaded artifact ZIP; the `.jar` files are inside.

The workflow uses Java 21 and Gradle 8.12 on GitHub's runner. The first build may fail if upstream Minecraft/Fabric dependency versions need adjustment; if so, share the Actions build log.
