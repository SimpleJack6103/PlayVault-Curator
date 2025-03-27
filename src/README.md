# JavaFX Template Setup Guide

This is a template for setting up JavaFX projects using Maven in IntelliJ IDEA. Follow the instructions below to get started.

---

## Prerequisites

Before getting started, make sure you have the following installed:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Java 17 or higher](https://adoptopenjdk.net/) (Java 23 recommended)
- [Maven](https://maven.apache.org/install.html)
- [JavaFX 23.0.2](https://gluonhq.com/products/javafx/)

---

## [GitHub Setup Guide](GITHUB_INSTRUCTIONS.md)
Before setting up JavaFX, make sure you clone this template correctly and understand how to commit changes to GitHub. Follow the link above for step-by-step instructions.

---

## Setting Up JavaFX in IntelliJ IDEA

### Adding JavaFX to Your Project

1. Download JavaFX from [here](https://gluonhq.com/products/javafx/). Download the SDK and extract it to a folder on your machine.
2. In IntelliJ IDEA, go to `File > Project Structure > Libraries` and click the `+` button to add a new library.
3. Select `Java` and then navigate to the `lib` folder of the downloaded JavaFX SDK. Select it and click `OK`.

### Adding JavaFX to the Module Settings

1. Go to `Run > Edit Configurations`.
2. In the `VM options` field, add the following (adjust the path to where you extracted JavaFX SDK):

    ```bash
    --module-path /path/to/javafx-sdk-23.0.2/lib --add-modules javafx.controls,javafx.fxml
    ```

---

## Running the Project

To run the project in IntelliJ IDEA:

1. Ensure that your Maven dependencies are installed and JavaFX is set up correctly.
2. Click the green `Run` button or go to `Run > Run 'Main'`.

If you need any help running the program, please refer to the troubleshooting section below.

---

## Troubleshooting

If you encounter any issues when running the program, make sure you have followed all the steps in the setup guide correctly. Common issues include:

- Incorrect module-path configuration in the `Run Configurations`.
- Missing JavaFX SDK library in IntelliJ IDEA's project structure.

For more help, feel free to reach out to the team.
