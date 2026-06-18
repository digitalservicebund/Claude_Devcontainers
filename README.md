# Claude_Devcontainers

A Kotlin (Gradle) starter project preconfigured to run inside a **Dev Container** with
[Claude Code](https://docs.claude.com/en/docs/claude-code/overview) already installed, and
opened through **IntelliJ IDEA**'s remote backend.

The point of this repo is the [`.devcontainer/devcontainer.json`](.devcontainer/devcontainer.json):
clone it, open it in a container, and you get a reproducible Java 25 / Gradle / Kotlin
environment with Claude Code ready to go — no local JDK or toolchain setup required.

## What's inside

| Component   | Version / Detail                                          |
| ----------- | -------------------------------------------------------- |
| Base image  | `mcr.microsoft.com/devcontainers/java:25-bookworm`       |
| JDK         | Java 25                                                   |
| Build tool  | Gradle 9.2.1 (via the wrapper) — Maven is not installed   |
| Language    | Kotlin 2.3.10 (`kotlin("jvm")`)                           |
| JVM toolchain | 25                                                      |
| Tests       | JUnit Platform (`kotlin("test")`)                        |
| AI tooling  | Claude Code (devcontainer feature)                       |

## Prerequisites

- A container runtime: [Docker Desktop](https://www.docker.com/products/docker-desktop/)
  (or another Docker-compatible engine).
- **IntelliJ IDEA 2023.2+** with the bundled **Dev Containers** support, or
  [JetBrains Gateway](https://www.jetbrains.com/remote-development/gateway/).

## Opening the project in IntelliJ

The dev container declares its JetBrains backend explicitly:

```jsonc
"customizations": {
  "jetbrains": { "backend": "IntelliJ" }
}
```

so IntelliJ will install and run the matching IDE backend inside the container.

### Option A — from a cloned repository

1. Clone this repository locally.
2. In IntelliJ, open `.devcontainer/devcontainer.json`.
3. Click the **gutter icon** next to the JSON (or use the *Dev Containers* tool
   window) and choose **Create Dev Container and Mount Sources…**.
4. Wait for the image to build and the IntelliJ backend to start, then connect
   when prompted.

### Option B — straight from Git (JetBrains Gateway)

1. Launch **JetBrains Gateway**.
2. Select **Dev Containers** → **New Dev Container**.
3. Paste this repository's Git URL and point it at
   `.devcontainer/devcontainer.json`.
4. Build, then connect with the IntelliJ client when it's ready.

The first build downloads the base image and features and is the slow one;
later starts reuse the cached layers and volumes.

## Using Claude Code

Claude Code is installed inside the container via the
`ghcr.io/anthropics/devcontainer-features/claude-code` feature. Open the
IntelliJ terminal (which runs *inside* the container) and start it with:

```bash
claude
```

## Building & testing

Use the Gradle wrapper from the in-container terminal (or the IntelliJ Gradle
tool window):

```bash
./gradlew build   # compile + test
./gradlew test    # run tests only
```
